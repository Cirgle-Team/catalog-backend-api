package org.cirgle.catalog.infrastructure.persistence.repository.jpa

import org.cirgle.catalog.infrastructure.persistence.entity.user.UserAccountEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface JpaUserAccountRepository : JpaRepository<UserAccountEntity, UUID> {
    fun existsByDisplayId(userId: String): Boolean

    fun findByDisplayId(userId: String): UserAccountEntity?
}