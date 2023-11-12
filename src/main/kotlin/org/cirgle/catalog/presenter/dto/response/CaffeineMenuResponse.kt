package org.cirgle.catalog.presenter.dto.response

import org.cirgle.catalog.domain.model.CaffeineMenu

data class CaffeineMenuResponse(
    val code: String = "success",
    val menuList: List<CaffeineMenu>
)