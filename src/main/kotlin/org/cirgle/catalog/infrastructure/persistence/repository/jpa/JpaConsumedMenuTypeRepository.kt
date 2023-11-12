package org.cirgle.catalog.infrastructure.persistence.repository.jpa

import org.cirgle.catalog.domain.model.MenuType
import org.cirgle.catalog.infrastructure.persistence.entity.user.ConsumedMenuTypeEntity
import org.cirgle.catalog.infrastructure.persistence.entity.user.ConsumedMenuTypeEntityKey
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate
import java.util.UUID

interface JpaConsumedMenuTypeRepository : JpaRepository<ConsumedMenuTypeEntity, ConsumedMenuTypeEntityKey> {

    fun findByUserIdAndMenuTypeAndDate(userId: UUID, menuType: MenuType, date: LocalDate): ConsumedMenuTypeEntity?

    fun findAllByUserIdAndMenuTypeAndDateBetween(userId: UUID, menuType: MenuType, start: LocalDate, end: LocalDate): List<ConsumedMenuTypeEntity>

}