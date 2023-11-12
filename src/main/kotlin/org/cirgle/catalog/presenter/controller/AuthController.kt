package org.cirgle.catalog.presenter.controller

import jakarta.validation.Valid
import org.cirgle.catalog.domain.service.CaffeineLogService
import org.cirgle.catalog.domain.service.CaffeineMenuService
import org.cirgle.catalog.domain.service.UserService
import org.cirgle.catalog.presenter.advice.annotation.HttpUser
import org.cirgle.catalog.presenter.advice.annotation.RequestUser
import org.cirgle.catalog.presenter.dto.request.EchoRequest
import org.cirgle.catalog.presenter.dto.request.LoginRequest
import org.cirgle.catalog.presenter.dto.request.RefreshTokenRequest
import org.cirgle.catalog.presenter.dto.request.RegisterRequest
import org.cirgle.catalog.presenter.dto.response.APIResponse
import org.cirgle.catalog.presenter.dto.response.TokenResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthController(
    private val caffeineLogService: CaffeineLogService,
    private val caffeineMenuService: CaffeineMenuService,
    private val userService: UserService,
) {
    @PostMapping("/register")
    fun register(@Valid @RequestBody request: RegisterRequest): APIResponse {
        val user = userService.register(request.displayId, request.password, request.birthday)
        caffeineLogService.initializeCaffeineLog(user.id)
        caffeineMenuService.initMenu(user.id)

        return APIResponse.ok(code = "success", message = "회원가입이 완료되었습니다.")
    }

    @PostMapping("/login")
    fun login(@Valid @RequestBody request: LoginRequest): TokenResponse {
        val token = userService.login(displayId = request.displayId, password = request.password)

        return TokenResponse(token = token)
    }

    @PostMapping("/refresh")
    fun refresh(@RequestBody request: RefreshTokenRequest): TokenResponse {
        val token = userService.refreshToken(refreshToken = request.refreshToken)

        return TokenResponse(token = token)
    }

    @PostMapping("/echo")
    fun echo(@RequestBody request: EchoRequest): EchoRequest {
        return request
    }
}