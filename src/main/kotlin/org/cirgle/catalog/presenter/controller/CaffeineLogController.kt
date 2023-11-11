package org.cirgle.catalog.presenter.controller

import org.cirgle.catalog.domain.exception.InvalidException
import org.cirgle.catalog.domain.exception.MenuCaffeineException
import org.cirgle.catalog.domain.exception.UserNotFoundException
import org.cirgle.catalog.domain.model.CaffeineLogDetail
import org.cirgle.catalog.domain.model.CaffeineMenu
import org.cirgle.catalog.domain.model.TodayCaffeineLog
import org.cirgle.catalog.domain.service.CaffeineLogService
import org.cirgle.catalog.domain.service.CaffeineMenuService
import org.cirgle.catalog.domain.service.UserService
import org.cirgle.catalog.presenter.advice.annotation.HttpUser
import org.cirgle.catalog.presenter.advice.annotation.RequestUser
import org.cirgle.catalog.presenter.dto.request.CaffeineMenuConsumeRequest
import org.cirgle.catalog.presenter.dto.response.APIResponse
import org.cirgle.catalog.presenter.dto.response.DailyCaffeineLogResponse
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import java.util.UUID

@RestController
@RequestMapping("/api/v1/caffeine-log")
class CaffeineLogController(
    private val userService: UserService,
    private val caffeineMenuService: CaffeineMenuService,
    private val caffeineLogService: CaffeineLogService,
) {
    @GetMapping
    fun getCaffeineLogDetail(@RequestUser user: HttpUser): CaffeineLogDetail {

        return caffeineLogService.getCaffeineLogDetail(user.id)
    }

    @GetMapping("/{displayId}")
    fun getCaffeineLogDetail(@PathVariable displayId: String): CaffeineLogDetail {
        val target = userService.findUserByDisplayId(displayId) ?: throw UserNotFoundException()

        return caffeineLogService.getCaffeineLogDetail(target.id)
    }

    @PostMapping
    fun consumeCaffeineMenu(
        @RequestUser user: HttpUser,
        @RequestBody request: CaffeineMenuConsumeRequest,
    ): APIResponse {
        if (request.caffeine < 0 || request.caffeine > MAXIMUM_CAFFEINE) {
            throw MenuCaffeineException()
        }
        val menu = request.toCaffeineMenu()
        caffeineMenuService.createMenu(user.id, menu)
        caffeineLogService.consumeCaffeineMenu(user.id, menu)
        return APIResponse.ok(code = "success", message = "카페인 메뉴 소비를 성공적으로 완료했습니다.")
    }

    @GetMapping("/today")
    fun getTodayCaffeineLog(@RequestUser user: HttpUser): TodayCaffeineLog {

        return caffeineLogService.getTodayCaffeineLog(user.id)
    }

    @GetMapping("/today/{displayId}")
    fun getTodayCaffeineLog(@PathVariable displayId: String): TodayCaffeineLog {
        val target = userService.findUserByDisplayId(displayId) ?: throw UserNotFoundException()

        return caffeineLogService.getTodayCaffeineLog(target.id)
    }

    @GetMapping("/daily")
    fun getDailyCaffeineLog(
        @RequestUser user: HttpUser,
        @RequestParam("start") start: String?,
        @RequestParam("end") end: String?,
    ): DailyCaffeineLogResponse {

        val startDate = LocalDate.parse(start ?: throw InvalidException())
        val endDate = LocalDate.parse(end ?: throw InvalidException())

        return DailyCaffeineLogResponse(
            caffeineLogs = caffeineLogService.findAllCaffeineLog(user.id, startDate, endDate)
        )
    }

    @GetMapping("/daily/{displayId}")
    fun getDailyCaffeineLog(
        @PathVariable displayId: String,
        @RequestParam("start") start: String?,
        @RequestParam("end") end: String?,
    ): DailyCaffeineLogResponse {
        val target = userService.findUserByDisplayId(displayId) ?: throw UserNotFoundException()
        val startDate = LocalDate.parse(start)
        val endDate = LocalDate.parse(end)

        return DailyCaffeineLogResponse(
            caffeineLogs = caffeineLogService.findAllCaffeineLog(target.id, startDate, endDate)
        )
    }

    private fun CaffeineMenuConsumeRequest.toCaffeineMenu(): CaffeineMenu
    = CaffeineMenu(
        name = menu,
        type = type,
        caffeine = caffeine
    )

    companion object {
        const val MAXIMUM_CAFFEINE = 1000
    }
}