package org.cirgle.catalog.domain.service

import org.cirgle.catalog.domain.model.Friend
import java.util.*

interface FriendService {

    fun addFriend(userId: UUID, targetId: UUID)

    fun removeFriend(userId: UUID, targetId: UUID)

    fun getFriends(userId: UUID): List<Friend>

    fun getFriendOf(userId: UUID): List<Friend>

}