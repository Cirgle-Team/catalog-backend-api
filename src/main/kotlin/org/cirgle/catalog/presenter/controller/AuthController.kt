package org.cirgle.catalog.presenter.controller

import jakarta.validation.Valid
import org.cirgle.catalog.domain.model.AuthToken
import org.cirgle.catalog.domain.service.CaffeineLogService
import org.cirgle.catalog.domain.service.UserService
import org.cirgle.catalog.presenter.dto.request.LoginRequest
import org.cirgle.catalog.presenter.dto.request.RegisterRequest
import org.cirgle.catalog.presenter.dto.response.APIResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthController(
    private val caffeineLogService: CaffeineLogService,
    private val userService: UserService,
) {
    @PostMapping("/register")
    fun register(@Valid @RequestBody request: RegisterRequest): APIResponse {
        val user = userService.register(request.displayId, request.password)
        caffeineLogService.initializeCaffeineLog(user.id)

        return APIResponse.ok(code = "success", message = "회원가입이 완료되었습니다.")
    }

    @PostMapping("/login")
    fun login(@Valid @RequestBody request: LoginRequest): AuthToken {
        return userService.login(displayId = request.displayId, password = request.password)
    }
}