package org.cirgle.catalog.infrastructure.provider

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.Jwts.SIG.HS256
import org.cirgle.catalog.domain.model.User
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec

@Component
class JwtTokenProvider(
    @Value("\${token.secret}") private val secret: String,
    @Value("\${token.expiration.access}") private val accessTokenExpiration: Long,
    @Value("\${token.expiration.refresh}") private val refreshTokenExpiration: Long,
) {
    private val signKey: SecretKey = SecretKeySpec(secret.toByteArray(), "HmacSHA256")

    fun createAccessToken(user: User): String {
        return Jwts.builder()
            .header().empty().add(buildHeader()).and()
            .claims(user.toPayLoad())
            .expiration(generateAccessTokenExpiration())
            .issuedAt(Date())
            .signWith(signKey, HS256)
            .compact()
    }

    fun createRefreshToken(user: User): String {
        return Jwts.builder()
            .header().empty().add(buildHeader()).and()
            .claims(user.toPayLoad())
            .expiration(generateRefreshTokenExpiration())
            .issuedAt(Date())
            .signWith(signKey, HS256)
            .compact()
    }

    private fun buildHeader() =
        mapOf<String, Any>(
            Pair("typ", "JWT"),
            Pair("alg", "HmacSHA256"),
            Pair("regDate", System.currentTimeMillis())
        )

    private fun generateAccessTokenExpiration() = Date(System.currentTimeMillis() + this.accessTokenExpiration * 1000)

    private fun generateRefreshTokenExpiration() = Date(System.currentTimeMillis() + this.refreshTokenExpiration * 1000)

    private fun User.toPayLoad() =
        mapOf<String, Any>(
            "id" to this.id,
        )

    fun getIdFromToken(token: String): UUID? {
        return Jwts.parser()
            .verifyWith(signKey)
            .build()
            .parseSignedClaims(token).payload["id"].toString().toUUID()
    }

    private fun String?.toUUID(): UUID? = UUID.fromString(this) ?: null
}