package org.cirgle.catalog.infrastructure.persistence.repository.jpa

import org.cirgle.catalog.infrastructure.persistence.entity.user.FriendEntity
import org.cirgle.catalog.infrastructure.persistence.entity.user.FriendEntityKey
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface JpaFriendRepository : JpaRepository<FriendEntity, FriendEntityKey> {

    fun findAllByFromUserId(fromUserId: UUID): List<FriendEntity>

    fun findAllByToUserId(toUserId: UUID): List<FriendEntity>

}