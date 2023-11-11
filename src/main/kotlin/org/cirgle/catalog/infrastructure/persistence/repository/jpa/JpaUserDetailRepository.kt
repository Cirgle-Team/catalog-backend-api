package org.cirgle.catalog.infrastructure.persistence.repository.jpa

import org.cirgle.catalog.infrastructure.persistence.entity.user.UserDetailEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface JpaUserDetailRepository : JpaRepository<UserDetailEntity, UUID> {

    fun findByDisplayId(userId: String): UserDetailEntity?

}