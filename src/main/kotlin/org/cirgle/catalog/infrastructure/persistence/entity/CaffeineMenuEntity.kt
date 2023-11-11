package org.cirgle.catalog.infrastructure.persistence.entity

import jakarta.persistence.*
import org.cirgle.catalog.domain.model.CaffeineMenu
import org.cirgle.catalog.domain.model.MenuType
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes
import java.io.Serializable
import java.util.*

@Entity(name = "caffeine_menu")
@IdClass(CaffeineMenuEntityKey::class)
data class CaffeineMenuEntity(
    @Id
    @JdbcTypeCode(SqlTypes.UUID)
    val userId: UUID,

    @Id
    val name: String,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val type: MenuType,

    @Column(nullable = false)
    val caffeine: Int,
) {
    fun toDomain() = CaffeineMenu(
        type = this.type,
        name = this.name,
        caffeine = this.caffeine,
    )
}

data class CaffeineMenuEntityKey(
    val userId: UUID = UUID.randomUUID(),
    val name: String = "",
) : Serializable
