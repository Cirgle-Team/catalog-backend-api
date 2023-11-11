package org.cirgle.catalog.presenter.dto.request

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import org.cirgle.catalog.presenter.advice.constant.ID_PATTERN
import org.cirgle.catalog.presenter.advice.constant.PASSWORD_PATTERN
import org.cirgle.catalog.util.SHA256

data class RegisterRequest(
    @field:JsonProperty("id")
    @field:NotBlank(message = "invalid-002")
    @field:Pattern(regexp = ID_PATTERN, message = "invalid-002")
    private val _displayId: String?,

    @field:JsonProperty("password")
    @field:NotBlank(message = "invalid-002")
    @field:Pattern(regexp = PASSWORD_PATTERN, message = "invalid-003")
    private val _password: String?,
) {
    val displayId: String get() = _displayId!!

    val password: String get() = SHA256.encrypt(_password!!)
}

