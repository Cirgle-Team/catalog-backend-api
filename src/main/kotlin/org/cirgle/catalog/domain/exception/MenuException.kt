package org.cirgle.catalog.domain.exception

import org.cirgle.catalog.domain.exception.code.ErrorCode

class MenuNotFoundException : DomainException(ErrorCode.MENU_NOT_FOUND)

class MenuAlreadyExistsException : DomainException(ErrorCode.MENU_ALREADY_EXISTS)

class MenuNameException : DomainException(ErrorCode.MENU_NAME)

class MenuCaffeineException : DomainException(ErrorCode.MENU_CAFFEINE)

class MenuTypeException : DomainException(ErrorCode.MENU_TYPE)