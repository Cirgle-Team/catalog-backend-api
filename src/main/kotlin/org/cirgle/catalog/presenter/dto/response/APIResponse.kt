package org.cirgle.catalog.presenter.dto.response

import org.cirgle.catalog.domain.exception.code.ErrorCode

data class APIResponse(
    val code: String,
    val message: String,
) {
    companion object {
        fun ok(code: String, message: String) = APIResponse(code, message)

        fun badRequest(errorCode: ErrorCode) = APIResponse(errorCode.code, errorCode.message)
    }
}