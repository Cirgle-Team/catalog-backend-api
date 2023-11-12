package org.cirgle.catalog.presenter.dto.response

import org.cirgle.catalog.domain.model.ConsumedMenuType
import org.cirgle.catalog.domain.model.MenuType

data class ConsumedMenuTypeResponse(
    val code: String = "success",

    val consumedMenuList: List<ConsumedMenuType>
)

