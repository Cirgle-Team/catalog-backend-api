package org.cirgle.catalog.presenter.dto.request

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDate

data class UserInfoRequest(
    val nickname: String?,
    @JsonProperty("birthday")
    private val _birthday: String?,
    val description: String?,
    val profileUrl: String?,
    val bannerUrl: String?,
) {
    val birthday: LocalDate? get() = _birthday?.let { LocalDate.parse(it) }
}