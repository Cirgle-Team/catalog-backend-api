package org.cirgle.catalog.presenter.dto.request

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank

data class CaffeineMenuDeleteRequest(
    @field:JsonProperty("menu")
    @field:NotBlank(message = "invalid-001")
    private val _menu: String?,
) {
    val menu: String get() = _menu!!
}