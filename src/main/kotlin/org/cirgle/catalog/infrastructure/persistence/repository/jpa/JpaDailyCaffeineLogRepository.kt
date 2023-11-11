package org.cirgle.catalog.infrastructure.persistence.repository.jpa

import org.cirgle.catalog.infrastructure.persistence.entity.DailyCaffeineLogEntity
import org.cirgle.catalog.infrastructure.persistence.entity.DailyCaffeineLogEntityKey
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate
import java.util.*

interface JpaDailyCaffeineLogRepository : JpaRepository<DailyCaffeineLogEntity, DailyCaffeineLogEntityKey> {

    fun findAllByUserIdAndDateBetween(userId: UUID, start: LocalDate, end: LocalDate): List<DailyCaffeineLogEntity>

}