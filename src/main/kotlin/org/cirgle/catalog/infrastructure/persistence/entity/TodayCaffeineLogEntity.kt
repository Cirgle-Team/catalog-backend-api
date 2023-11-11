package org.cirgle.catalog.infrastructure.persistence.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes
import java.time.LocalDate
import java.util.*

@Entity(name = "today_caffeine_log")
data class TodayCaffeineLogEntity(

    @Id
    @JdbcTypeCode(SqlTypes.UUID)
    val userId: UUID,

    @Column(nullable = false)
    val lastCommitted: LocalDate,

    @Column(nullable = false)
    val consumedCaffeine: Int = 0,
)