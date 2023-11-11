package org.cirgle.catalog.infrastructure.persistence.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.IdClass
import org.cirgle.catalog.domain.model.DailyCaffeineLog
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes
import java.io.Serializable
import java.time.LocalDate
import java.util.*

@Entity(name = "daily_caffeine_log")
@IdClass(DailyCaffeineLogEntityKey::class)
data class DailyCaffeineLogEntity(
    @Id
    @JdbcTypeCode(SqlTypes.UUID)
    val userId: UUID,

    @Id
    val date: LocalDate,

    @Column(nullable = false)
    val isSuccess: Boolean = false,

    @Column(nullable = false)
    val consumedCaffeine: Int,

    @Column(nullable = false)
    val maxCaffeine: Int,
) {
    fun toDomain() = DailyCaffeineLog(
        date = this.date,
        consumedCaffeine = this.consumedCaffeine,
        maxCaffeine = this.maxCaffeine,
        isSuccess = this.isSuccess,
    )
}

data class DailyCaffeineLogEntityKey(
    val userId: UUID = UUID.randomUUID(),
    val date: LocalDate = LocalDate.now(),
) : Serializable