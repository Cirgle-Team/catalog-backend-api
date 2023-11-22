package org.cirgle.catalog.infrastructure.persistence.repository.impl

import org.cirgle.catalog.domain.exception.UserNotFoundException
import org.cirgle.catalog.domain.model.DailyCaffeineLog
import org.cirgle.catalog.domain.model.TodayCaffeineLog
import org.cirgle.catalog.domain.repository.CaffeineLogRepository
import org.cirgle.catalog.infrastructure.persistence.entity.DailyCaffeineLogEntity
import org.cirgle.catalog.infrastructure.persistence.entity.TodayCaffeineLogEntity
import org.cirgle.catalog.infrastructure.persistence.repository.jpa.JpaCaffeineLogDetailRepository
import org.cirgle.catalog.infrastructure.persistence.repository.jpa.JpaDailyCaffeineLogRepository
import org.cirgle.catalog.infrastructure.persistence.repository.jpa.JpaTodayCaffeineLogRepository
import org.springframework.stereotype.Repository
import java.time.LocalDate
import java.util.*
import kotlin.jvm.optionals.getOrNull

@Repository
class CaffeineLogRepositoryImpl(
    private val jpaCaffeineLogDetailRepository: JpaCaffeineLogDetailRepository,
    private val jpaTodayCaffeineLogRepository: JpaTodayCaffeineLogRepository,
    private val jpaDailyCaffeineLogRepository: JpaDailyCaffeineLogRepository,
) : CaffeineLogRepository {

    override fun updateTodayCaffeineLog(userId: UUID, todayCaffeineLog: TodayCaffeineLog) {
        val todayCaffeineLogEntity = TodayCaffeineLogEntity(
            userId = userId,
            lastCommitted = todayCaffeineLog.lastCommitted,
            consumedCaffeine = todayCaffeineLog.consumedCaffeine,
        )
        jpaTodayCaffeineLogRepository.save(todayCaffeineLogEntity)
    }

    override fun saveDailyCaffeineLog(userId: UUID, dailyCaffeineLog: DailyCaffeineLog) {
        val dailyCaffeineLogEntity = DailyCaffeineLogEntity(
            userId = userId,
            date = dailyCaffeineLog.date,
            consumedCaffeine = dailyCaffeineLog.consumedCaffeine,
            maxCaffeine = dailyCaffeineLog.maxCaffeine,
            isSuccess = dailyCaffeineLog.isSuccess,
        )
        jpaDailyCaffeineLogRepository.save(dailyCaffeineLogEntity)
    }

    override fun getTodayCaffeineLog(userId: UUID): TodayCaffeineLog {
        val maxCaffeine = jpaCaffeineLogDetailRepository.getMaxCaffeineByUserId(userId)
        val todayCaffeineLog = jpaTodayCaffeineLogRepository.findById(userId).getOrNull()
            ?: throw UserNotFoundException()
        return TodayCaffeineLog(
            lastCommitted = todayCaffeineLog.lastCommitted,
            consumedCaffeine = todayCaffeineLog.consumedCaffeine,
            maxCaffeine = maxCaffeine,
        )
    }

    override fun findAllCaffeineLog(userId: UUID, start: LocalDate, end: LocalDate): List<DailyCaffeineLog> {
        return jpaDailyCaffeineLogRepository.findAllByUserIdAndDateBetween(userId, start, end).map {
            it.toDomain()
        }
    }

}