package org.cirgle.catalog.infrastructure.persistence.entity.user

import jakarta.persistence.*
import org.cirgle.catalog.domain.model.ConsumedMenuType
import org.cirgle.catalog.domain.model.MenuType
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes
import java.io.Serializable
import java.time.LocalDate
import java.util.UUID

@Entity(name = "consumed_menu_type")
@Table(indexes = [Index(name = "idx_date", columnList = "date")])
@IdClass(ConsumedMenuTypeEntityKey::class)
data class ConsumedMenuTypeEntity(
    @Id
    @JdbcTypeCode(SqlTypes.UUID)
    val userId: UUID,

    @Id
    @Enumerated(EnumType.STRING)
    val menuType: MenuType,

    @Column(nullable = false)
    val date: LocalDate,

    @Column(nullable = false)
    val consumedCaffeine: Int = 0,
) {
    fun toDomain()
    = ConsumedMenuType(
        date = date,
        menuType = menuType,
        caffeine = consumedCaffeine
    )
}

data class ConsumedMenuTypeEntityKey(
    val userId: UUID = UUID.randomUUID(),
    val menuType: MenuType = MenuType.ETC,
) : Serializable