package org.cirgle.catalog.presenter.controller

import jakarta.validation.Valid
import org.cirgle.catalog.domain.exception.MenuCaffeineException
import org.cirgle.catalog.domain.model.CaffeineMenu
import org.cirgle.catalog.domain.service.CaffeineMenuService
import org.cirgle.catalog.domain.service.UserService
import org.cirgle.catalog.presenter.advice.annotation.HttpUser
import org.cirgle.catalog.presenter.advice.annotation.RequestUser
import org.cirgle.catalog.presenter.dto.request.CaffeineMenuCreateRequest
import org.cirgle.catalog.presenter.dto.request.CaffeineMenuDeleteRequest
import org.cirgle.catalog.presenter.dto.response.CaffeineMenuResponse
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1/caffeine-menu")
class CaffeineMenuController(
    private val userService: UserService,
    private val caffeineMenuService: CaffeineMenuService,
) {
    @GetMapping
    fun getCaffeineMenuList(@RequestUser user: HttpUser): CaffeineMenuResponse {

        return CaffeineMenuResponse(
            menuList = caffeineMenuService.findMenuListByUserId(user.id)
        )
    }

    @PostMapping
    fun createCaffeineMenu(
        @RequestUser user: HttpUser,
        @Valid @RequestBody request: CaffeineMenuCreateRequest
    ): APIResponse {
        if (request.caffeine < 0 || request.caffeine > MAXIMUM_CAFFEINE) {
            throw MenuCaffeineException()
        }
        caffeineMenuService.createMenu(userId = user.id, menu = request.toCaffeineMenu())

        return APIResponse.ok(code = "success", message = "메뉴가 추가되었습니다.")
    }

    @DeleteMapping
    fun deleteCaffeineMenu(
        @RequestUser user: HttpUser,
        @Valid @RequestBody request: CaffeineMenuDeleteRequest
    ): APIResponse {
        caffeineMenuService.deleteMenu(menuId = request.menuId)

        return APIResponse.ok(code = "success", message = "메뉴가 삭제되었습니다.")
    }

    private fun CaffeineMenuCreateRequest.toCaffeineMenu() = CaffeineMenu(
        id = UUID.randomUUID(),
        name = name,
        caffeine = caffeine,
    )

    private companion object {
        const val MAXIMUM_CAFFEINE = 1000
    }
}