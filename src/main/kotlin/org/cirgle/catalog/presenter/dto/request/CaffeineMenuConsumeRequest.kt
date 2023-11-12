package org.cirgle.catalog.presenter.dto.request

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank
import org.cirgle.catalog.domain.exception.MenuCaffeineException
import org.cirgle.catalog.domain.model.MenuType
import org.cirgle.catalog.presenter.advice.annotation.ValidEnum
import org.springframework.data.convert.ValueConverter
import java.util.UUID

data class CaffeineMenuConsumeRequest(
    @field:JsonProperty("menu")
    @field:NotBlank(message = "menu-003")
    private val _menu: String?,

    @field:JsonProperty("type")
    @field:ValidEnum(enumClass = MenuType::class, message = "menu-005")
    @field:NotBlank(message = "menu-005")
    private val _type: String?,

    @field:JsonProperty("caffeine")
    @field:NotBlank(message = "menu-004")
    private val _caffeine: Int?
) {
    val menu: String get() = _menu!!

    val type: MenuType get() = MenuType.valueOf(_type!!)

    val caffeine: Int get() = _caffeine ?: throw MenuCaffeineException()
}