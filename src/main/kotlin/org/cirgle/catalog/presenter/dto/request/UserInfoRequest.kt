package org.cirgle.catalog.presenter.dto.request

import java.time.LocalDate

data class UserInfoRequest(
    val nickname: String? = null,
    private val _birthday: String? = null,
    val description: String? = null,
    val profileUrl: String? = null,
    val bannerUrl: String? = null,
) {
    val birthday: LocalDate? get() = _birthday?.let { LocalDate.parse(it) }
}