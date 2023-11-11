package org.cirgle.catalog.application.service

import org.cirgle.catalog.domain.exception.InvalidException
import org.cirgle.catalog.domain.exception.UserAlreadyExistsException
import org.cirgle.catalog.domain.exception.UserNotFoundException
import org.cirgle.catalog.domain.exception.UserPasswordMismatchException
import org.cirgle.catalog.domain.model.AuthToken
import org.cirgle.catalog.domain.model.User
import org.cirgle.catalog.domain.repository.UserRepository
import org.cirgle.catalog.domain.service.UserService
import org.cirgle.catalog.infrastructure.persistence.entity.user.UserAccountEntity
import org.cirgle.catalog.infrastructure.persistence.entity.user.UserDetailEntity
import org.cirgle.catalog.infrastructure.persistence.entity.user.UserTokenEntity
import org.cirgle.catalog.infrastructure.persistence.repository.jpa.JpaUserAccountRepository
import org.cirgle.catalog.infrastructure.persistence.repository.jpa.JpaUserDetailRepository
import org.cirgle.catalog.infrastructure.persistence.repository.jpa.JpaUserTokenRepository
import org.cirgle.catalog.infrastructure.provider.JwtTokenProvider
import org.cirgle.catalog.util.RandomNickGenerator
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.util.*

@Service
class UserServiceImpl(
        private val tokenProvider: JwtTokenProvider,
        private val userRepository: UserRepository,
        private val jpaUserDetailRepository: JpaUserDetailRepository,
        private val jpaUserAccountRepository: JpaUserAccountRepository,
        private val jpaUserTokenRepository: JpaUserTokenRepository,
) : UserService {

    @Transactional
    override fun register(displayId: String, password: String): User {
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
                createdAt = LocalDate.now(),
        )
        val userAccount = UserAccountEntity(
                id = id,
                displayId = displayId,
                password = password,
        )
        jpaUserDetailRepository.save(userDetail)
        jpaUserAccountRepository.save(userAccount)
        return User(
                id = userDetail.id,
                displayId = userDetail.displayId,
                nickname = userDetail.nickname,
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
    override fun getUserById(userId: UUID): User {
        return userRepository.findUserById(userId) ?: throw UserNotFoundException()
    }

    override fun findUserByDisplayId(displayId: String): User? {
        return userRepository.findUserByDisplayId(displayId)
    }

    fun refreshToken(refreshToken: String): AuthToken {
        val userId = tokenProvider.getIdFromToken(refreshToken) ?: throw InvalidException()
        val user = userRepository.findUserById(userId) ?: throw UserNotFoundException()
        return createToken(user)
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
}