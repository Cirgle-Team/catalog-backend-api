package org.cirgle.catalog.presenter.dto.request

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import org.cirgle.catalog.domain.exception.MenuCaffeineException
import org.cirgle.catalog.domain.model.MenuType
import org.cirgle.catalog.presenter.advice.annotation.ValidEnum
import org.cirgle.catalog.presenter.advice.constant.MENU_NAME_PATTERN

data class CaffeineMenuCreateRequest(

    @field:JsonProperty("name")
    @field:NotBlank(message = "menu-003")
    @field:Pattern(regexp = MENU_NAME_PATTERN, message = "menu-003")
    private val _name: String?,

    @field:JsonProperty("type")
    @field:NotBlank(message = "menu-005")
    @field:ValidEnum(enumClass = MenuType::class, message = "menu-005")
    private val _type: String?,

    @field:JsonProperty("caffeine")
    private val _caffeine: Int?,
) {
    val name: String get() = _name!!
    val caffeine: Int get() = _caffeine ?: throw MenuCaffeineException()
    val type: MenuType get() = MenuType.valueOf(_type!!)
}