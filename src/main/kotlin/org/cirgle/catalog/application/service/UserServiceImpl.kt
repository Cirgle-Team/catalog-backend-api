package org.cirgle.catalog.application.service

import org.cirgle.catalog.domain.exception.InvalidException
import org.cirgle.catalog.domain.exception.UserAlreadyExistsException
import org.cirgle.catalog.domain.exception.UserNotFoundException
import org.cirgle.catalog.domain.exception.UserPasswordMismatchException
import org.cirgle.catalog.domain.model.AuthToken
import org.cirgle.catalog.domain.model.User
import org.cirgle.catalog.domain.model.UserProfile
import org.cirgle.catalog.domain.repository.UserRepository
import org.cirgle.catalog.domain.service.UserService
import org.cirgle.catalog.infrastructure.persistence.entity.user.*
import org.cirgle.catalog.infrastructure.persistence.repository.jpa.JpaUserAccountRepository
import org.cirgle.catalog.infrastructure.persistence.repository.jpa.JpaUserDetailRepository
import org.cirgle.catalog.infrastructure.persistence.repository.jpa.JpaUserProfileRepository
import org.cirgle.catalog.infrastructure.persistence.repository.jpa.JpaUserTokenRepository
import org.cirgle.catalog.infrastructure.provider.JwtTokenProvider
import org.cirgle.catalog.util.RandomNickGenerator
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.util.*
import kotlin.jvm.optionals.getOrNull

@Service
class UserServiceImpl(
    private val tokenProvider: JwtTokenProvider,
    private val userRepository: UserRepository,
    private val jpaUserProfileRepository: JpaUserProfileRepository,
    private val jpaUserDetailRepository: JpaUserDetailRepository,
    private val jpaUserAccountRepository: JpaUserAccountRepository,
    private val jpaUserTokenRepository: JpaUserTokenRepository,
) : UserService {

    @Transactional
    override fun register(displayId: String, password: String, birthday: LocalDate): User {
        if (jpaUserAccountRepository.existsByDisplayId(displayId)) {
            throw UserAlreadyExistsException()
        }
        val id = UUID.randomUUID()
        val nickname = RandomNickGenerator.generate()
        val userDetail = UserDetailEntity(
            id = id,
            displayId = displayId,
            nickname = nickname,
            description = "",
            birthday = birthday,
            createdAt = LocalDate.now(),
        )
        val userAccount = UserAccountEntity(
            id = id,
            displayId = displayId,
            password = password,
        )
        val userProfile = UserProfileEntity(
            id = id,
            profileUrl = "",
            bannerUrl = "",
        )
        jpaUserDetailRepository.save(userDetail)
        jpaUserAccountRepository.save(userAccount)
        jpaUserProfileRepository.save(userProfile)
        return User(
            id = userDetail.id,
            displayId = userDetail.displayId,
            nickname = userDetail.nickname,
            birthday = userDetail.birthday,
            description = userDetail.description,
        )
    }

    @Transactional
    override fun login(displayId: String, password: String): AuthToken {
        val userAccount = jpaUserAccountRepository.findByDisplayId(displayId) ?: throw UserNotFoundException()
        if (userAccount.password != password) {
            throw UserPasswordMismatchException()
        }

        val user = userRepository.findUserByDisplayId(displayId) ?: throw UserNotFoundException()
        return createToken(user)
    }

    @Transactional
    override fun update(user: User, profileUrl: String?, bannerUrl: String?) {
        val userDetail = jpaUserDetailRepository.findById(user.id).getOrNull() ?: throw UserNotFoundException()
        val newUserDetail = userDetail.copy(
            nickname = user.nickname,
            birthday = user.birthday,
            description = user.description,
        )
        val userProfile = jpaUserProfileRepository.findById(user.id).getOrNull() ?: throw UserNotFoundException()
        val newUserProfile = userProfile.copy(
            profileUrl = profileUrl ?: userProfile.profileUrl,
            bannerUrl = bannerUrl ?: userProfile.bannerUrl,
        )
        jpaUserProfileRepository.save(newUserProfile)
        jpaUserDetailRepository.save(newUserDetail)
    }

    @Transactional
    override fun getUserById(userId: UUID): User {
        return userRepository.findUserById(userId) ?: throw UserNotFoundException()
    }

    @Transactional
    override fun getUserProfile(userId: UUID): UserProfile {
        return jpaUserProfileRepository.findById(userId).getOrNull()?.toUserProfile() ?: throw UserNotFoundException()
    }

    @Transactional
    override fun findUserByDisplayId(displayId: String): User? {
        return userRepository.findUserByDisplayId(displayId)
    }

    @Transactional
    override fun refreshToken(refreshToken: String): AuthToken {
        val userId = tokenProvider.getIdFromToken(refreshToken) ?: throw InvalidException()
        if (!jpaUserTokenRepository.existsById(UserTokenEntityKey(userId, refreshToken))) {
            throw InvalidException()
        }

        val user = userRepository.findUserById(userId) ?: throw UserNotFoundException()
        return AuthToken(
            accessToken = tokenProvider.createAccessToken(user),
            refreshToken = refreshToken
        )
    }

    @Transactional
    override fun logout(userId: UUID) {
        jpaUserTokenRepository.deleteAllById(userId)
    }

    fun createToken(user: User): AuthToken {
        val token = AuthToken(
            accessToken = tokenProvider.createAccessToken(user),
            refreshToken = tokenProvider.createRefreshToken(user)
        )
        jpaUserTokenRepository.save(token.toUserTokenEntity(user))
        return token
    }

    private fun AuthToken.toUserTokenEntity(user: User): UserTokenEntity {
        return UserTokenEntity(
            id = user.id,
            refreshToken = this.refreshToken,
        )
    }

    private fun UserProfileEntity.toUserProfile(): UserProfile {
        return UserProfile(
            profileUrl = this.profileUrl,
            bannerUrl = this.bannerUrl,
        )
    }
}