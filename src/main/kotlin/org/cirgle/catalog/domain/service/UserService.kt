package org.cirgle.catalog.domain.service

import org.cirgle.catalog.domain.model.AuthToken
import org.cirgle.catalog.domain.model.User
import java.util.*

interface UserService {
    fun register(displayId: String, password: String): User

    fun login(displayId: String, password: String): AuthToken

    fun getUserById(userId: UUID): User

    fun findUserByDisplayId(displayId: String): User?
}