package org.cirgle.catalog.domain.model

import java.time.LocalDate

data class ConsumedMenuType (
    val date: LocalDate,
    val menuType: MenuType,
    val caffeine: Int
)