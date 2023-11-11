package org.cirgle.catalog.domain.model

data class AuthToken(
    val accessToken: String,
    val refreshToken: String,
)