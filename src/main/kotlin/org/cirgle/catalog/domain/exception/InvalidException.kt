package org.cirgle.catalog.domain.exception

import org.cirgle.catalog.domain.exception.code.ErrorCode

class InvalidException : DomainException(ErrorCode.INVALID)

class InvalidIdException : DomainException(ErrorCode.INVALID_ID)

class InvalidPasswordException : DomainException(ErrorCode.INVALID_PASSWORD)