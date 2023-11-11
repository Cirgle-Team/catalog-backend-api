package org.cirgle.catalog.presenter.controller

import org.cirgle.catalog.domain.exception.UserNotFoundException
import org.cirgle.catalog.domain.service.FriendService
import org.cirgle.catalog.domain.service.UserService
import org.cirgle.catalog.presenter.advice.annotation.HttpUser
import org.cirgle.catalog.presenter.advice.annotation.RequestUser
import org.cirgle.catalog.presenter.dto.request.FriendAddRequest
import org.cirgle.catalog.presenter.dto.response.APIResponse
import org.cirgle.catalog.presenter.dto.response.FriendResponse
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/v1/friends")
class FriendController(
    private val userService: UserService,
    private val friendService: FriendService,
) {
    @GetMapping
    fun getFriends(@RequestUser user: HttpUser): FriendResponse {
        return FriendResponse(
            friends = friendService.getFriends(user.id),
            friendOf = friendService.getFriendOf(user.id)
        )
    }

    @GetMapping("/{displayId}")
    fun getFriends(@PathVariable displayId: String): FriendResponse {
        val target = userService.findUserByDisplayId(displayId)
            ?: throw UserNotFoundException()

        return FriendResponse(
            friends = friendService.getFriends(target.id),
            friendOf = friendService.getFriendOf(target.id)
        )
    }

    @PostMapping
    fun addFriend(
        @RequestUser user: HttpUser,
        @RequestBody request: FriendAddRequest,
    ): APIResponse {
        val target = userService.findUserByDisplayId(request.displayId)
            ?: throw UserNotFoundException()
        friendService.addFriend(user.id, target.id)

        return APIResponse.ok("success", "성공적으로 친구를 추가했습니다.")
    }

    @DeleteMapping
    fun removeFriend(
        @RequestUser user: HttpUser,
        @RequestBody request: FriendAddRequest,
    ): APIResponse {
        val target = userService.findUserByDisplayId(request.displayId)
            ?: throw UserNotFoundException()
        friendService.removeFriend(user.id, target.id)

        return APIResponse.ok("success", "성공적으로 친구를 삭제했습니다.")
    }
}