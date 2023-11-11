package org.cirgle.catalog.domain.service

import org.cirgle.catalog.domain.model.CaffeineMenu
import java.util.*

interface CaffeineMenuService {

    fun createMenu(userId: UUID, menu: CaffeineMenu)

    fun deleteMenu(userId: UUID, menuName: String)

    fun getMenu(userId: UUID, menuName: String): CaffeineMenu

    fun findMenuListByUserId(userId: UUID): List<CaffeineMenu>

}