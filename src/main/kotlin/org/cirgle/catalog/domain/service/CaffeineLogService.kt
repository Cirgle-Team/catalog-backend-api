package org.cirgle.catalog.domain.service

import org.cirgle.catalog.domain.model.*
import java.time.LocalDate
import java.util.*

interface CaffeineLogService {

    fun initializeCaffeineLog(userId: UUID)

    fun setMaxCaffeine(userId: UUID, value: Int)

    fun consumeCaffeineMenu(userId: UUID, caffeineMenu: CaffeineMenu)

    fun getTodayCaffeineLog(userId: UUID): TodayCaffeineLog

    fun getCaffeineLogDetail(userId: UUID): CaffeineLogDetail

    fun findAllConsumedMenuType(
        userId: UUID,
        menuType: MenuType,
        start: LocalDate,
        end: LocalDate
    ): List<ConsumedMenuType>

    fun findAllCaffeineLog(userId: UUID, start: LocalDate, end: LocalDate): List<DailyCaffeineLog>

}