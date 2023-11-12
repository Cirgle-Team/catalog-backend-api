package org.cirgle.catalog.presenter.controller

import org.cirgle.catalog.domain.exception.UserNotFoundException
import org.cirgle.catalog.domain.service.CaffeineLogService
import org.cirgle.catalog.domain.service.UserService
import org.cirgle.catalog.presenter.advice.annotation.HttpUser
import org.cirgle.catalog.presenter.advice.annotation.RequestUser
import org.cirgle.catalog.presenter.dto.request.UserInfoRequest
import org.cirgle.catalog.presenter.dto.response.APIResponse
import org.cirgle.catalog.presenter.dto.response.UserInfoResponse
import org.springframework.web.bind.annotation.*

@RestController
class InfoController(
    private val userService: UserService,
    private val caffeineLogService: CaffeineLogService,
) {

    @GetMapping("/info")
    fun info(@RequestUser user: HttpUser): UserInfoResponse {
        val userDetail = userService.getUserById(user.id)
        val userProfile = userService.getUserProfile(user.id)
        val caffeineLogDetail = caffeineLogService.getCaffeineLogDetail(user.id)

        return UserInfoResponse(
            nickname = userDetail.nickname,
            displayId = userDetail.displayId,
            description = userDetail.description,
            profileUrl = userProfile.profileUrl,
            bannerUrl = userProfile.bannerUrl,
            birthday = userDetail.birthday,
            maxStreak = caffeineLogDetail.maxStreak,
        )
    }

    @GetMapping("/info/{displayId}")
    fun info(@PathVariable displayId: String): UserInfoResponse {
        val target = userService.findUserByDisplayId(displayId) ?: throw UserNotFoundException()

        val userProfile = userService.getUserProfile(target.id)
        val caffeineLogDetail = caffeineLogService.getCaffeineLogDetail(target.id)
        val userDetail = userService.getUserById(target.id)

        return UserInfoResponse(
            nickname = userDetail.nickname,
            displayId = userDetail.displayId,
            description = userDetail.description,
            profileUrl = userProfile.profileUrl,
            bannerUrl = userProfile.bannerUrl,
            birthday = userDetail.birthday,
            maxStreak = caffeineLogDetail.maxStreak,
        )
    }

    @PostMapping("/info")
    fun updateInfo(@RequestUser user: HttpUser, @RequestBody request: UserInfoRequest): APIResponse {
        val userDetail = userService.getUserById(user.id)

        val newUserDetail = userDetail.copy(
            nickname = request.nickname ?: userDetail.nickname,
            birthday = request.birthday ?: userDetail.birthday,
            description = request.description ?: userDetail.description,
        )
        userService.update(newUserDetail, request.profileUrl, request.bannerUrl)
        return APIResponse.ok(code = "success", message = "회원정보가 수정되었습니다.")
    }
}