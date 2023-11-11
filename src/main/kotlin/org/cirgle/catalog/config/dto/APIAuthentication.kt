package org.cirgle.catalog.config.dto

import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import java.util.*

class APIAuthentication(
    private val userId: UUID,
    private val accessToken: String,
    private val remoteAddr: String,
) : Authentication {

    private var isAuthenticated = true

    override fun getName(): String {
        return userId.toString()
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableListOf()
    }

    override fun getCredentials() = accessToken

    override fun getDetails() = null
    override fun getPrincipal() = ""

    fun getRemoteAddr() = remoteAddr

    override fun isAuthenticated(): Boolean = isAuthenticated

    override fun setAuthenticated(isAuthenticated: Boolean) {
        this.isAuthenticated = isAuthenticated
    }
}