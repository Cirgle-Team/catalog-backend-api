package org.cirgle.catalog.domain.model

import java.util.*

data class Friend(
    val id: UUID,
    val displayId: String,
    val nickname: String,
)