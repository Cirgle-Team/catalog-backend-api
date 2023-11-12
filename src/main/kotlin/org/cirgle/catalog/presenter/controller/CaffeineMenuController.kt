package org.cirgle.catalog.presenter.controller

import jakarta.validation.Valid
import org.cirgle.catalog.domain.exception.MenuCaffeineException
import org.cirgle.catalog.domain.exception.MenuTypeException
import org.cirgle.catalog.domain.exception.UserNotFoundException
import org.cirgle.catalog.domain.model.CaffeineMenu
import org.cirgle.catalog.domain.model.MenuType
import org.cirgle.catalog.domain.service.CaffeineLogService
import org.cirgle.catalog.domain.service.CaffeineMenuService
import org.cirgle.catalog.domain.service.UserService
import org.cirgle.catalog.presenter.advice.annotation.HttpUser
import org.cirgle.catalog.presenter.advice.annotation.RequestUser
import org.cirgle.catalog.presenter.dto.request.CaffeineMenuCreateRequest
import org.cirgle.catalog.presenter.dto.request.CaffeineMenuDeleteRequest
import org.cirgle.catalog.presenter.dto.response.APIResponse
import org.cirgle.catalog.presenter.dto.response.CaffeineMenuResponse
import org.cirgle.catalog.presenter.dto.response.ConsumedMenuTypeResponse
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import java.util.*

@RestController
@RequestMapping("/api/v1/caffeine-menu")
class CaffeineMenuController(
    private val userService: UserService,
    private val caffeineLogService: CaffeineLogService,
    private val caffeineMenuService: CaffeineMenuService,
) {
    @GetMapping
    fun getCaffeineMenuList(@RequestUser user: HttpUser): CaffeineMenuResponse {
        return CaffeineMenuResponse(
            menuList = caffeineMenuService.findMenuListByUserId(user.id)
        )
    }

    @DeleteMapping
    fun deleteCaffeineMenu(
        @RequestUser user: HttpUser,
        @Valid @RequestBody request: CaffeineMenuDeleteRequest
    ): APIResponse {
        caffeineMenuService.deleteMenu(user.id, request.menu)

        return APIResponse.ok(code = "success", message = "메뉴가 삭제되었습니다.")
    }

    @GetMapping("/type")
    fun getConsumedMenuType(
        @RequestUser user: HttpUser,
        @RequestParam("type") type: String?,
        @RequestParam("start") start: String?,
        @RequestParam("end") end: String?,
    ): ConsumedMenuTypeResponse {

        val menuType = MenuType.valueOf(type ?: throw MenuTypeException())
        val startDate = LocalDate.parse(start)
        val endDate = LocalDate.parse(end)

        val consumedMenuList = caffeineLogService.findAllConsumedMenuType(user.id, menuType, startDate, endDate)
        return ConsumedMenuTypeResponse(consumedMenuList = consumedMenuList)
    }

    @GetMapping("/type/{displayId}")
    fun getConsumedMenuType(
        @RequestUser user: HttpUser,
        @PathVariable displayId: String,
        @RequestParam("type") type: String?,
        @RequestParam("start") start: String?,
        @RequestParam("end") end: String?,
    ): ConsumedMenuTypeResponse {

        val startDate = LocalDate.parse(start)
        val endDate = LocalDate.parse(end)
        val target = userService.findUserByDisplayId(displayId) ?: throw UserNotFoundException()
        val menuType = MenuType.valueOf(type ?: throw MenuTypeException())

        val consumedMenuList = caffeineLogService.findAllConsumedMenuType(target.id, menuType, startDate, endDate)
        return ConsumedMenuTypeResponse(consumedMenuList = consumedMenuList)
    }
}