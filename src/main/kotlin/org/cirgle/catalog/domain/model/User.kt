package org.cirgle.catalog.domain.model

import java.time.LocalDate
import java.util.*

data class User(
    val id: UUID,
    val displayId: String,
    val nickname: String,
    val description: String,
    val birthday: LocalDate,
)