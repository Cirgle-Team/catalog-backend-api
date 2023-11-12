package org.cirgle.catalog.presenter.dto.request

import jakarta.validation.constraints.NotBlank

data class RefreshTokenRequest(
    @field:NotBlank(message = "invalid-001")
    val refreshToken: String,
)