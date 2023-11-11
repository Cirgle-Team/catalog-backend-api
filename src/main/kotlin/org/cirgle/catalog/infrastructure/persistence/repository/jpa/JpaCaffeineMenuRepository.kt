package org.cirgle.catalog.infrastructure.persistence.repository.jpa

import org.cirgle.catalog.infrastructure.persistence.entity.CaffeineMenuEntity
import org.cirgle.catalog.infrastructure.persistence.entity.CaffeineMenuEntityKey
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface JpaCaffeineMenuRepository : JpaRepository<CaffeineMenuEntity, CaffeineMenuEntityKey> {

    fun findAllByUserId(userId: UUID): List<CaffeineMenuEntity>

    fun findById(id: UUID): CaffeineMenuEntity?

    fun existsById(id: UUID): Boolean

    fun existsByUserIdAndName(userId: UUID, name: String): Boolean

    fun deleteById(id: UUID)

    fun deleteByUserIdAndName(userId: UUID, name: String)

}