package org.cirgle.catalog.infrastructure.persistence.repository.impl

import org.cirgle.catalog.domain.exception.MenuNotFoundException
import org.cirgle.catalog.domain.model.CaffeineMenu
import org.cirgle.catalog.domain.repository.CaffeineMenuRepository
import org.cirgle.catalog.infrastructure.persistence.entity.CaffeineMenuEntity
import org.cirgle.catalog.infrastructure.persistence.repository.jpa.JpaCaffeineMenuRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class CaffeineMenuRepositoryImpl(
    private val jpaCaffeineMenuRepository: JpaCaffeineMenuRepository,
) : CaffeineMenuRepository {

    override fun create(userId: UUID, menu: CaffeineMenu) {
        jpaCaffeineMenuRepository.save(menu.toEntity(userId))
    }

    override fun delete(userId: UUID, menuName: String) {
        jpaCaffeineMenuRepository.deleteByUserIdAndName(userId, menuName)
    }

    override fun delete(menuId: UUID) {
        jpaCaffeineMenuRepository.deleteById(menuId)
    }

    override fun get(menuId: UUID): CaffeineMenu {
        return jpaCaffeineMenuRepository.findById(menuId)?.toDomain() ?: throw MenuNotFoundException()
    }

    override fun exists(userId: UUID, menuName: String): Boolean {
        return jpaCaffeineMenuRepository.existsByUserIdAndName(userId, menuName)
    }

    override fun exists(menuId: UUID): Boolean {
        return jpaCaffeineMenuRepository.existsById(menuId)
    }

    override fun findAllByUserId(userId: UUID): List<CaffeineMenu> {
        return jpaCaffeineMenuRepository.findAllByUserId(userId).map {
            it.toDomain()
        }
    }

    private fun CaffeineMenu.toEntity(userId: UUID) = CaffeineMenuEntity(
        id = id,
        userId = userId,
        name = name,
        type = type,
        caffeine = caffeine
    )
}