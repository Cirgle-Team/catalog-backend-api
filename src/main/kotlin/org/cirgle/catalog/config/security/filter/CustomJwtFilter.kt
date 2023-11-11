package org.cirgle.catalog.config.security.filter

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.cirgle.catalog.config.dto.APIAuthentication
import org.cirgle.catalog.domain.exception.InvalidException
import org.cirgle.catalog.infrastructure.provider.JwtTokenProvider
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class CustomJwtFilter(
    @Value("\${app.headers.auth-token}") private val authTokenHeader: String,
    private val jwtTokenProvider: JwtTokenProvider,
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val token = request.getHeader(authTokenHeader)
        if (token != null) {
            val authentication = APIAuthentication(
                userId = jwtTokenProvider.getIdFromToken(token) ?: throw InvalidException(),
                accessToken = token,
                remoteAddr = request.remoteAddr
            )
            SecurityContextHolder.getContext().authentication = authentication
        }
        filterChain.doFilter(request, response)
    }
}