package org.cirgle.catalog.domain.model

import java.time.LocalDate

data class DailyCaffeineLog(
    val date: LocalDate,
    val consumedCaffeine: Int,
    val maxCaffeine: Int,
    val isSuccess: Boolean
)