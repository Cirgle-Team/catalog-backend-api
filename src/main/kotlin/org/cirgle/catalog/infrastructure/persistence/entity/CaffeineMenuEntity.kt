package org.cirgle.catalog.infrastructure.persistence.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.IdClass
import org.cirgle.catalog.domain.model.CaffeineMenu
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
    @JdbcTypeCode(SqlTypes.UUID)
    val id: UUID,

    @Column(nullable = false)
    val caffeine: Int,
) {
    fun toDomain() = CaffeineMenu(
        id = this.id,
        name = this.name,
        caffeine = this.caffeine,
    )
}

data class CaffeineMenuEntityKey(
    val userId: UUID = UUID.randomUUID(),
    val name: String = "",
) : Serializable
