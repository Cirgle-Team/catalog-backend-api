package org.cirgle.catalog.presenter.dto.response

import org.cirgle.catalog.domain.model.CaffeineLogDetail

data class CaffeineLogDetailResponse(
    val code: String = "success",
    val caffeineLogDetail: CaffeineLogDetail,
)