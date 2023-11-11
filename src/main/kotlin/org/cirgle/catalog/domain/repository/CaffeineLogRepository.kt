package org.cirgle.catalog.domain.repository

import org.cirgle.catalog.domain.model.DailyCaffeineLog
import org.cirgle.catalog.domain.model.TodayCaffeineLog
import java.time.LocalDate
import java.util.*

interface CaffeineLogRepository {

    fun updateTodayCaffeineLog(userId: UUID, todayCaffeineLog: TodayCaffeineLog)

    fun saveDailyCaffeineLog(userId: UUID, dailyCaffeineLog: DailyCaffeineLog)

    fun getTodayCaffeineLog(userId: UUID): TodayCaffeineLog

    fun findAllCaffeineLog(userId: UUID, start: LocalDate, end: LocalDate): List<DailyCaffeineLog>

}