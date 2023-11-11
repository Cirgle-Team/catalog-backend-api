package org.cirgle.catalog.domain.model

import java.util.*

data class CaffeineMenu(
    val id: UUID,
    val name: String,
    val caffeine: Int,
)