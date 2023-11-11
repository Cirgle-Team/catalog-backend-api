package org.cirgle.catalog.domain.repository

import org.cirgle.catalog.domain.model.User
import java.util.*

interface UserRepository {

    fun findUserById(id: UUID): User?

    fun findUserByDisplayId(displayId: String): User?
}