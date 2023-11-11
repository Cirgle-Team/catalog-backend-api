package org.cirgle.catalog.infrastructure.persistence.repository.impl

import org.cirgle.catalog.domain.model.User
import org.cirgle.catalog.domain.repository.UserRepository
import org.cirgle.catalog.infrastructure.persistence.repository.jpa.JpaUserDetailRepository
import org.springframework.stereotype.Repository
import java.util.*
import kotlin.jvm.optionals.getOrNull

@Repository
class UserRepositoryImpl(
    private val jpaUserDetailRepository: JpaUserDetailRepository,
) : UserRepository {

    override fun findUserById(id: UUID): User? {
        val userDetail = jpaUserDetailRepository.findById(id).getOrNull() ?: return null
        return User(
            id = id,
            displayId = userDetail.displayId,
            nickname = userDetail.nickname,
            description = userDetail.description,
        )
    }

    override fun findUserByDisplayId(displayId: String): User? {
        val userDetail = jpaUserDetailRepository.findByDisplayId(displayId) ?: return null
        return User(
            id = userDetail.id,
            displayId = userDetail.displayId,
            nickname = userDetail.nickname,
            description = userDetail.description,
        )
    }
}