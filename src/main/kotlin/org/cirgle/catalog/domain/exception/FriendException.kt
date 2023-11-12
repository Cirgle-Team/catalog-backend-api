package org.cirgle.catalog.domain.exception

import org.cirgle.catalog.domain.exception.code.ErrorCode

class FriendNotFoundException : DomainException(ErrorCode.FRIEND_NOT_FOUND)

class FriendAlreadyExistsException : DomainException(ErrorCode.FRIEND_ALREADY_EXISTS)

class FriendAddSelfException : DomainException(ErrorCode.FRIEND_ADD_SELF)