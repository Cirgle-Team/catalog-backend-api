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
    override fun deleteMenu(userId: UUID, menuName: String) {
        if (!caffeineMenuRepository.exists(userId, menuName)) {
            throw MenuNotFoundException()
        }
        caffeineMenuRepository.delete(userId, menuName)
    }

    @Transactional
    override fun getMenu(userId: UUID, menuName: String): CaffeineMenu {
        return caffeineMenuRepository.get(userId, menuName)
    }

    @Transactional
    override fun findMenuListByUserId(userId: UUID): List<CaffeineMenu> {
        return caffeineMenuRepository.findAllByUserId(userId).sortedBy {
            it.type.ordinal
        }
    }
}