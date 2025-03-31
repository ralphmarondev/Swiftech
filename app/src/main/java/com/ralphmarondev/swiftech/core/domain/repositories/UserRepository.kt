package com.ralphmarondev.swiftech.core.domain.repositories

import com.ralphmarondev.swiftech.core.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun createUser(user: User)
    suspend fun updateUser(user: User)
    suspend fun deleteUser(id: Int)
    suspend fun getUserDetailByUsername(username: String): User
    suspend fun isUserExists(username: String, password: String): Boolean

    fun getAllUsers(): Flow<List<User>>
    fun getAllUsersByRole(role: String): Flow<List<User>>
}