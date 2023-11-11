package org.cirgle.catalog.domain.repository

import org.cirgle.catalog.domain.model.Friend
import java.util.*

interface FriendRepository {

    fun register(fromUserId: UUID, toUserId: UUID)

    fun delete(fromUserId: UUID, toUserId: UUID)

    fun exists(fromUserId: UUID, toUserId: UUID): Boolean

    fun findAllFriendByFromUserId(fromUserId: UUID): List<Friend>

    fun findAllFriendByToUserId(fromUserId: UUID): List<Friend>

}