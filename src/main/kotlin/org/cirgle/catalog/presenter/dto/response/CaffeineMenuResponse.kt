package org.cirgle.catalog.presenter.dto.response

import org.cirgle.catalog.domain.model.CaffeineMenu

data class CaffeineMenuResponse(
    val menuList: List<CaffeineMenu>
)