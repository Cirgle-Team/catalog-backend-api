package org.cirgle.catalog.domain.repository

import org.cirgle.catalog.domain.model.CaffeineMenu
import java.util.*

interface CaffeineMenuRepository {

    fun create(userId: UUID, menu: CaffeineMenu)

    fun delete(userId: UUID, menuName: String)

    fun delete(menuId: UUID)

    fun get(menuId: UUID): CaffeineMenu

    fun exists(userId: UUID, menuName: String): Boolean

    fun exists(menuId: UUID): Boolean

    fun findAllByUserId(userId: UUID): List<CaffeineMenu>

}