package org.cirgle.catalog.presenter.dto.request

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank
import java.util.UUID

data class CaffeineMenuConsumeRequest(
    @field:JsonProperty("menuId")
    @field:NotBlank(message = "invalid-001")
    private val _menuId: String?
) {
    val menuId: UUID get() = UUID.fromString(_menuId!!)
}