package com.ralphmarondev.swiftech.core.domain.repositories

import com.ralphmarondev.swiftech.core.domain.model.User

interface UserRepository {
    suspend fun createUser(user: User)
    suspend fun updateUser(user: User)
    suspend fun deleteUser(id: Int)
    suspend fun getUserDetailByUsername(username: String): User
    suspend fun isUserExists(username: String, password: String): Boolean
}