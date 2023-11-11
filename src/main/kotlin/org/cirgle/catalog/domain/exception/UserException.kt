package org.cirgle.catalog.domain.exception

import org.cirgle.catalog.domain.exception.code.ErrorCode

class UserNotFoundException : DomainException(ErrorCode.USER_NOT_FOUND)

class UserAlreadyExistsException : DomainException(ErrorCode.USER_ALREADY_EXISTS)

class UserPasswordMismatchException : DomainException(ErrorCode.USER_PASSWORD_MISMATCH)