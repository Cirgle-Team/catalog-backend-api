package org.cirgle.catalog.presenter.dto.response

import org.cirgle.catalog.domain.model.AuthToken

data class TokenResponse(
    val code: String = "success",
    val token: AuthToken
)