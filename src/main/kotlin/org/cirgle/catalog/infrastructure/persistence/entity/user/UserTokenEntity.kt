package org.cirgle.catalog.infrastructure.persistence.entity.user

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.IdClass
import java.io.Serializable
import java.util.*

@IdClass(UserTokenEntityKey::class)
@Entity(name = "user_token")
data class UserTokenEntity(
    @Id
    val id: UUID,

    @Id
    val refreshToken: String,
)

data class UserTokenEntityKey(
    val id: UUID = UUID.randomUUID(),
    val refreshToken: String = "",
) : Serializable