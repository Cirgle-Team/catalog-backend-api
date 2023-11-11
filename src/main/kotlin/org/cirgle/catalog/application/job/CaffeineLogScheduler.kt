package org.cirgle.catalog.application.job

import org.cirgle.catalog.domain.model.DailyCaffeineLog
import org.cirgle.catalog.domain.model.TodayCaffeineLog
import org.cirgle.catalog.domain.repository.CaffeineLogRepository
import org.cirgle.catalog.infrastructure.persistence.entity.CaffeineLogDetailEntity
import org.cirgle.catalog.infrastructure.persistence.repository.jpa.JpaCaffeineLogDetailRepository
import org.springframework.scheduling.annotation.Async
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Component
class CaffeineLogScheduler(
    private val jpaCaffeineLogDetailRepository: JpaCaffeineLogDetailRepository,
    private val caffeineLogRepository: CaffeineLogRepository,
) {

    @Async
    @Transactional
    @Scheduled(cron = DAILY_CAFFEINE_LOG_CRON)
    fun dailyLogger() {
        val details = jpaCaffeineLogDetailRepository.findAll()
        details.forEach { detail ->
            val userId = detail.userId
            val todayCaffeineLog = caffeineLogRepository.getTodayCaffeineLog(userId)
            val today = LocalDate.now().minusDays(1)

            val dailyCaffeineLog = todayCaffeineLog.toDailyCaffeineLog(today)
            val isSuccess = dailyCaffeineLog.isSuccess

            caffeineLogRepository.saveDailyCaffeineLog(userId, dailyCaffeineLog)
            jpaCaffeineLogDetailRepository.save(detail.includeSuccess(isSuccess))
        }
        println("[System]: $DAILY_CAFFEINE_LOG_MESSAGE")
    }

    private fun TodayCaffeineLog.toDailyCaffeineLog(today: LocalDate): DailyCaffeineLog {

        return DailyCaffeineLog(
            date = today,
            consumedCaffeine = this.consumedCaffeine,
            maxCaffeine = this.maxCaffeine,
            isSuccess = if (today == lastCommitted) isSuccess
            else false,
        )
    }

    private fun CaffeineLogDetailEntity.includeSuccess(isSuccess: Boolean): CaffeineLogDetailEntity {

        return CaffeineLogDetailEntity(
            userId = userId,
            maxCaffeine = maxCaffeine,
            streak = if (isSuccess) streak + 1 else 0,
            maxStreak = if (isSuccess) maxOf(maxStreak, streak + 1) else maxStreak,
        )
    }

    companion object {
        internal const val DAILY_CAFFEINE_LOG_CRON = "0 0 0 * * *"

        internal const val DAILY_CAFFEINE_LOG_MESSAGE = "사용자들의 카페인 로그가 기록되었어요."
    }
}