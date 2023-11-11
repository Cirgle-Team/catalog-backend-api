package org.cirgle.catalog.infrastructure.persistence.entity.user

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.IdClass
import java.io.Serializable
import java.util.*

@Entity(name = "friend")
@IdClass(FriendEntityKey::class)
data class FriendEntity(
    @Id
    val fromUserId: UUID,

    @Id
    val toUserId: UUID,
)

data class FriendEntityKey(
    val fromUserId: UUID = UUID.randomUUID(),
    val toUserId: UUID = UUID.randomUUID(),
) : Serializable