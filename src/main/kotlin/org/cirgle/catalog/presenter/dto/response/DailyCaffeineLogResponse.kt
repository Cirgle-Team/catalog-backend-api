package org.cirgle.catalog.presenter.dto.response

import org.cirgle.catalog.domain.model.DailyCaffeineLog

data class DailyCaffeineLogResponse(
    val code: String = "success",
    val caffeineLogs: List<DailyCaffeineLog>,
)