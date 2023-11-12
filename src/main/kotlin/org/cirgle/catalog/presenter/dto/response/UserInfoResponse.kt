package org.cirgle.catalog.presenter.dto.response

import java.time.LocalDate

data class UserInfoResponse(
    val code: String = "success",

    val displayId: String,

    val nickname: String,

    val birthday: LocalDate,

    val description: String,

    val maxStreak: Int,

    val profileUrl: String,

    val bannerUrl: String,
)