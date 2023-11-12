package org.cirgle.catalog.domain.service

import org.cirgle.catalog.domain.model.AuthToken
import org.cirgle.catalog.domain.model.User
import org.cirgle.catalog.domain.model.UserProfile
import java.time.LocalDate
import java.util.*

interface UserService {

    fun register(displayId: String, password: String, birthday: LocalDate): User

    fun login(displayId: String, password: String): AuthToken

    fun update(user: User, profileUrl: String?, bannerUrl: String?)

    fun getUserById(userId: UUID): User

    fun getUserProfile(userId: UUID): UserProfile

    fun refreshToken(refreshToken: String): AuthToken

    fun logout(userId: UUID)

    fun findUserByDisplayId(displayId: String): User?
}