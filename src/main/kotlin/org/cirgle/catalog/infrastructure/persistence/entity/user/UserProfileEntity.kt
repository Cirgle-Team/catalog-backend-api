package org.cirgle.catalog.infrastructure.persistence.entity.user

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes
import java.util.UUID

@Entity(name = "user_profile")
data class UserProfileEntity(
    @Id
    @JdbcTypeCode(SqlTypes.UUID)
    val id: UUID,

    @Column(nullable = false)
    val profileUrl: String,

    @Column(nullable = false)
    val bannerUrl: String
)