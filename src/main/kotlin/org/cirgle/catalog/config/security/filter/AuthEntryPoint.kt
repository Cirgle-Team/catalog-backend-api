package org.cirgle.catalog.config.security.filter

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.cirgle.catalog.domain.exception.code.ErrorCode
import org.cirgle.catalog.presenter.dto.response.ErrorResponse
import org.springframework.http.MediaType
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component

@Component
class AuthEntryPoint(
    private val objectMapper: ObjectMapper,
) : AuthenticationEntryPoint {

    private fun writeErrorResponse(response: HttpServletResponse) {
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.characterEncoding = "UTF-8"
        response.writer.write(
            objectMapper.writeValueAsString(ErrorResponse.of(ErrorCode.INVALID))
        )
    }

    override fun commence(
        request: HttpServletRequest?,
        response: HttpServletResponse,
        authException: AuthenticationException?
    ) {
        writeErrorResponse(response)
    }
}