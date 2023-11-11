package org.cirgle.catalog.infrastructure.persistence.repository.jpa

import org.cirgle.catalog.infrastructure.persistence.entity.user.UserTokenEntity
import org.cirgle.catalog.infrastructure.persistence.entity.user.UserTokenEntityKey
import org.springframework.data.jpa.repository.JpaRepository

interface JpaUserTokenRepository : JpaRepository<UserTokenEntity, UserTokenEntityKey>