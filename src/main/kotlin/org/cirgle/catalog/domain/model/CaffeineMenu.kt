package org.cirgle.catalog.domain.model

data class CaffeineMenu(
    val type: MenuType,
    val name: String,
    val caffeine: Int,
)

enum class MenuType(val id: String) {
    COFFEE("커피"),
    ENERGY_DRINK("에너지드링크"),
    DESSERT("디저트"),
    ETC("기타"),
    ;
}