package org.cirgle.catalog.application.service

import org.cirgle.catalog.domain.exception.FriendAlreadyExistsException
import org.cirgle.catalog.domain.exception.FriendNotFoundException
import org.cirgle.catalog.domain.model.Friend
import org.cirgle.catalog.domain.repository.FriendRepository
import org.cirgle.catalog.domain.service.FriendService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class FriendServiceImpl(
    private val friendRepository: FriendRepository
) : FriendService {

    @Transactional
    override fun addFriend(userId: UUID, targetId: UUID) {
        if (friendRepository.exists(userId, targetId)) {
            throw FriendAlreadyExistsException()
        }
        friendRepository.register(userId, targetId)
    }

    @Transactional
    override fun removeFriend(userId: UUID, targetId: UUID) {
        if (!friendRepository.exists(userId, targetId)) {
            throw FriendNotFoundException()
        }
        friendRepository.delete(userId, targetId)
    }

    @Transactional(readOnly = true)
    override fun getFriends(userId: UUID): List<Friend> {
        return friendRepository.findAllFriendByFromUserId(userId)
    }

    @Transactional(readOnly = true)
    override fun getFriendOf(userId: UUID): List<Friend> {
        return friendRepository.findAllFriendByToUserId(userId)
    }
}