package org.cirgle.catalog.infrastructure.persistence.repository.jpa

import org.cirgle.catalog.infrastructure.persistence.entity.CaffeineLogDetailEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface JpaCaffeineLogDetailRepository : JpaRepository<CaffeineLogDetailEntity, UUID> {

    @Query("select maxCaffeine from caffeine_log_detail where userId = :userId")
    fun getMaxCaffeineByUserId(userId: UUID): Int

}