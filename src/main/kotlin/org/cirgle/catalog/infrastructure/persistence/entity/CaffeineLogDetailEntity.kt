package org.cirgle.catalog.infrastructure.persistence.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes
import java.util.*

@Entity(name = "caffeine_log_detail")
data class CaffeineLogDetailEntity(
    @Id
    @JdbcTypeCode(SqlTypes.UUID)
    val userId: UUID,

    @Column(nullable = false)
    val maxCaffeine: Int,

    @Column(nullable = false)
    val streak: Int = 0,

    @Column(nullable = false)
    val maxStreak: Int = 0,
)