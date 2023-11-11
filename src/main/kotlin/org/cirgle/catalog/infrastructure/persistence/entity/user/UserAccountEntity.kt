package org.cirgle.catalog.infrastructure.persistence.entity.user

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes
import java.util.*

@Entity(name = "user_account")
data class UserAccountEntity(
    @Id
    @JdbcTypeCode(SqlTypes.UUID)
    val id: UUID,

    @Column(nullable = false, unique = true)
    val displayId: String,

    @Column(nullable = false)
    val password: String,
)