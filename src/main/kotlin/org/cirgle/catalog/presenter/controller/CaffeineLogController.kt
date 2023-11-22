package org.cirgle.catalog.presenter.controller

import org.cirgle.catalog.domain.exception.InvalidException
import org.cirgle.catalog.domain.exception.MenuCaffeineException
import org.cirgle.catalog.domain.exception.UserNotFoundException
import org.cirgle.catalog.domain.model.CaffeineMenu
import org.cirgle.catalog.domain.service.CaffeineLogService
import org.cirgle.catalog.domain.service.CaffeineMenuService
import org.cirgle.catalog.domain.service.UserService
import org.cirgle.catalog.presenter.advice.annotation.HttpUser
import org.cirgle.catalog.presenter.advice.annotation.RequestUser
import org.cirgle.catalog.presenter.dto.request.CaffeineMenuConsumeRequest
import org.cirgle.catalog.presenter.dto.response.APIResponse
import org.cirgle.catalog.presenter.dto.response.CaffeineLogDetailResponse
import org.cirgle.catalog.presenter.dto.response.DailyCaffeineLogResponse
import org.cirgle.catalog.presenter.dto.response.TodayCaffeineLogResponse
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@RestController
@RequestMapping("/api/v1/caffeine-log")
class CaffeineLogController(
    private val userService: UserService,
    private val caffeineMenuService: CaffeineMenuService,
    private val caffeineLogService: CaffeineLogService,
) {
    @GetMapping
    fun getCaffeineLogDetail(@RequestUser user: HttpUser): CaffeineLogDetailResponse {
        val caffeineLogDetail = caffeineLogService.getCaffeineLogDetail(user.id)

        return CaffeineLogDetailResponse(caffeineLogDetail = caffeineLogDetail)
    }

    @GetMapping("/{displayId}")
    fun getCaffeineLogDetail(@PathVariable displayId: String): CaffeineLogDetailResponse {
        val target = userService.findUserByDisplayId(displayId) ?: throw UserNotFoundException()
        val caffeineLogDetail = caffeineLogService.getCaffeineLogDetail(target.id)

        return CaffeineLogDetailResponse(caffeineLogDetail = caffeineLogDetail)
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
    fun getTodayCaffeineLog(@RequestUser user: HttpUser): TodayCaffeineLogResponse {
        val todayCaffeineLog = caffeineLogService.getTodayCaffeineLog(user.id)

        return TodayCaffeineLogResponse(caffeineLog = todayCaffeineLog)
    }

    @GetMapping("/today/{displayId}")
    fun getTodayCaffeineLog(@PathVariable displayId: String): TodayCaffeineLogResponse {
        val target = userService.findUserByDisplayId(displayId) ?: throw UserNotFoundException()
        val todayCaffeineLog = caffeineLogService.getTodayCaffeineLog(target.id)

        return TodayCaffeineLogResponse(caffeineLog = todayCaffeineLog)
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

    private fun CaffeineMenuConsumeRequest.toCaffeineMenu(): CaffeineMenu = CaffeineMenu(
        name = menu,
        type = type,
        caffeine = caffeine
    )

    companion object {
        const val MAXIMUM_CAFFEINE = 1000
    }
}