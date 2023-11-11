package org.cirgle.catalog.application.service

import org.cirgle.catalog.domain.exception.MenuNotFoundException
import org.cirgle.catalog.domain.model.CaffeineMenu
import org.cirgle.catalog.domain.repository.CaffeineMenuRepository
import org.cirgle.catalog.domain.service.CaffeineMenuService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class CaffeineMenuServiceImpl(
    private val caffeineMenuRepository: CaffeineMenuRepository,
) : CaffeineMenuService {

    @Transactional
    override fun createMenu(userId: UUID, menu: CaffeineMenu) {
        caffeineMenuRepository.create(userId, menu)
    }

    @Transactional
    override fun deleteMenu(menuId: UUID) {
        if (!caffeineMenuRepository.exists(menuId)) {
            throw MenuNotFoundException()
        }
        caffeineMenuRepository.delete(menuId)
    }

    @Transactional
    override fun getMenu(menuId: UUID): CaffeineMenu {
        return caffeineMenuRepository.get(menuId)
    }

    @Transactional
    override fun findMenuListByUserId(userId: UUID): List<CaffeineMenu> {
        return caffeineMenuRepository.findAllByUserId(userId).sortedBy {
            it.type.ordinal
        }
    }
}