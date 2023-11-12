package org.cirgle.catalog.application.service

import org.cirgle.catalog.domain.exception.MenuNotFoundException
import org.cirgle.catalog.domain.model.CaffeineMenu
import org.cirgle.catalog.domain.model.MenuType
import org.cirgle.catalog.domain.repository.CaffeineMenuRepository
import org.cirgle.catalog.domain.service.CaffeineMenuService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class CaffeineMenuServiceImpl(
    private val caffeineMenuRepository: CaffeineMenuRepository,
) : CaffeineMenuService {

    override fun initMenu(userId: UUID) {
        val menus = listOf(
            CaffeineMenu(type=MenuType.COFFEE, name="아메리카노", caffeine=100),
            CaffeineMenu(type=MenuType.COFFEE, name="카페라떼", caffeine=75),
            CaffeineMenu(type=MenuType.COFFEE, name="카페모카", caffeine=90),
            CaffeineMenu(type=MenuType.COFFEE, name="에스프레소", caffeine=40),

            CaffeineMenu(type=MenuType.ENERGY_DRINK, name="레드불", caffeine=80),
            CaffeineMenu(type=MenuType.ENERGY_DRINK, name="몬스터", caffeine=36),
            CaffeineMenu(type=MenuType.ENERGY_DRINK, name="핫식스", caffeine=100),

            CaffeineMenu(type=MenuType.DESSERT, name="티라미수", caffeine=50),
            CaffeineMenu(type=MenuType.DESSERT, name="초콜릿", caffeine=12),

            CaffeineMenu(type=MenuType.ETC, name="밀크티", caffeine=110),
            CaffeineMenu(type=MenuType.ETC, name="녹차", caffeine=30),
        )
        menus.forEach {
            caffeineMenuRepository.create(userId, it)
        }
    }


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