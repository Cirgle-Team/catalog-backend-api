package org.cirgle.catalog.presenter.dto.request

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank

data class FriendAddRequest(
    @field:JsonProperty("displayId")
    @field:NotBlank(message = "user-001")
    private val _displayId: String?
) {
    val displayId: String get() = _displayId!!
}
