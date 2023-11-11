package org.cirgle.catalog.infrastructure.persistence.repository.impl

import org.cirgle.catalog.domain.exception.UserNotFoundException
import org.cirgle.catalog.domain.model.Friend
import org.cirgle.catalog.domain.repository.FriendRepository
import org.cirgle.catalog.infrastructure.persistence.entity.user.FriendEntity
import org.cirgle.catalog.infrastructure.persistence.entity.user.FriendEntityKey
import org.cirgle.catalog.infrastructure.persistence.repository.jpa.JpaFriendRepository
import org.cirgle.catalog.infrastructure.persistence.repository.jpa.JpaUserDetailRepository
import org.springframework.stereotype.Repository
import java.util.*
import kotlin.jvm.optionals.getOrNull

@Repository
class FriendRepositoryImpl(
    private val jpaUserDetailRepository: JpaUserDetailRepository,
    private val jpaFriendRepository: JpaFriendRepository
) : FriendRepository {

    override fun register(fromUserId: UUID, toUserId: UUID) {
        val friend = FriendEntity(
            fromUserId = fromUserId,
            toUserId = toUserId
        )
        jpaFriendRepository.save(friend)
    }

    override fun delete(fromUserId: UUID, toUserId: UUID) {
        jpaFriendRepository.deleteById(
            FriendEntityKey(
                fromUserId = fromUserId,
                toUserId = toUserId
            )
        )
    }

    override fun exists(fromUserId: UUID, toUserId: UUID): Boolean {
        return jpaFriendRepository.existsById(
            FriendEntityKey(
                fromUserId = fromUserId,
                toUserId = toUserId
            )
        )
    }

    override fun findAllFriendByFromUserId(fromUserId: UUID): List<Friend> {
        return jpaFriendRepository.findAllByFromUserId(fromUserId).map {
            it.toUserId.toFriend()
        }
    }

    override fun findAllFriendByToUserId(fromUserId: UUID): List<Friend> {
        return jpaFriendRepository.findAllByToUserId(fromUserId).map {
            it.fromUserId.toFriend()
        }
    }

    private fun UUID.toFriend(): Friend {
        val userDetail = jpaUserDetailRepository.findById(this).getOrNull()
            ?: throw UserNotFoundException()
        val displayId = userDetail.displayId
        val nickname = userDetail.nickname

        return Friend(
            id = this,
            displayId = displayId,
            nickname = nickname,
        )
    }
}