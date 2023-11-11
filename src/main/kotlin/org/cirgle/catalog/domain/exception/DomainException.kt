package org.cirgle.catalog.domain.exception

import org.cirgle.catalog.domain.exception.code.ErrorCode

open class DomainException(val errorCode: ErrorCode) : RuntimeException()

class UnknownException : DomainException(ErrorCode.UNKNOWN_ERROR)