package org.cirgle.catalog.presenter.dto.response

import org.cirgle.catalog.domain.exception.code.ErrorCode

data class ErrorResponse(
    val code: String,
    val message: String,
) {
    companion object {
        fun of(errorCode: ErrorCode) = ErrorResponse(errorCode.code, errorCode.message)
    }
}