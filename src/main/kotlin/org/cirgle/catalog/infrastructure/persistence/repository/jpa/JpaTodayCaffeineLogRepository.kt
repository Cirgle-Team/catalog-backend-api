package org.cirgle.catalog.infrastructure.persistence.repository.jpa

import org.cirgle.catalog.infrastructure.persistence.entity.TodayCaffeineLogEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface JpaTodayCaffeineLogRepository : JpaRepository<TodayCaffeineLogEntity, UUID>