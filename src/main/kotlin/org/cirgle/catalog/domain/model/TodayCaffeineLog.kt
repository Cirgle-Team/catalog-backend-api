package org.cirgle.catalog.domain.model

import java.time.LocalDate

data class TodayCaffeineLog(
    val lastCommitted: LocalDate,
    val consumedCaffeine: Int = 0,
    val maxCaffeine: Int,
) {
    val isSuccess get() = (consumedCaffeine <= maxCaffeine)
}