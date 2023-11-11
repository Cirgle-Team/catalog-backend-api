package org.cirgle.catalog.presenter.dto.request

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import org.cirgle.catalog.presenter.advice.constant.ID_PATTERN
import org.cirgle.catalog.presenter.advice.constant.PASSWORD_PATTERN
import org.cirgle.catalog.util.SHA256

data class LoginRequest(
    @field:JsonProperty("id")
    @field:NotBlank(message = "user-001")
    @field:Pattern(regexp = ID_PATTERN, message = "user-001")
    private val _displayId: String?,

    @field:JsonProperty("password")
    @field:NotBlank(message = "user-003")
    @field:Pattern(regexp = PASSWORD_PATTERN, message = "user-003")
    private val _password: String?,
) {
    val displayId: String get() = _displayId!!

    val password: String get() = SHA256.encrypt(_password!!)
}