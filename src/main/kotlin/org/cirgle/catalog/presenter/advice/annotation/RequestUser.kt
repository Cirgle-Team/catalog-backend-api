package org.cirgle.catalog.presenter.advice.annotation

import org.cirgle.catalog.config.dto.APIAuthentication
import org.cirgle.catalog.domain.exception.InvalidException
import org.springframework.core.MethodParameter
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer
import java.util.*

@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class RequestUser

@Component
class RequestUserArgumentResolver : HandlerMethodArgumentResolver {
    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.getParameterAnnotation(RequestUser::class.java) != null
                && parameter.parameterType == HttpUser::class.java
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?,
    ): Any {
        val authorization = SecurityContextHolder.getContext().authentication
        if (authorization !is APIAuthentication) throw InvalidException()

        return HttpUser(
            id = UUID.fromString(authorization.name),
            remoteAddr = authorization.getRemoteAddr()
        )
    }
}

data class HttpUser(val id: UUID, val remoteAddr: String)
