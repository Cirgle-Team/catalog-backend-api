package org.cirgle.catalog.domain.service

import org.cirgle.catalog.domain.model.AuthToken
import org.cirgle.catalog.domain.model.User
import java.time.LocalDate
import java.util.*

interface UserService {

    fun register(displayId: String, password: String, birthday: LocalDate): User

    fun login(displayId: String, password: String): AuthToken

    fun getUserById(userId: UUID): User

    fun findUserByDisplayId(displayId: String): User?
}