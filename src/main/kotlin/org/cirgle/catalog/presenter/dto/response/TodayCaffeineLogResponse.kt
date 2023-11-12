package org.cirgle.catalog.presenter.dto.response

import org.cirgle.catalog.domain.model.TodayCaffeineLog

data class TodayCaffeineLogResponse(
    val code: String = "success",
    val caffeineLog: TodayCaffeineLog,
)