package org.cirgle.catalog.infrastructure.persistence.repository.jpa

import org.cirgle.catalog.infrastructure.persistence.entity.user.UserProfileEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface JpaUserProfileRepository : JpaRepository<UserProfileEntity, UUID>