package com.ralphmarondev.swiftech.core.data.repositories

import com.ralphmarondev.swiftech.core.data.local.database.dao.UserDao
import com.ralphmarondev.swiftech.core.domain.model.User
import com.ralphmarondev.swiftech.core.domain.repositories.UserRepository
import kotlinx.coroutines.flow.Flow

class UserRepositoryImpl(
    private val userDao: UserDao
) : UserRepository {
    override suspend fun createUser(user: User) {
        userDao.createUser(user)
    }

    override suspend fun updateUser(user: User) {
        userDao.updateUser(user)
    }

    override suspend fun deleteUser(id: Int) {
        userDao.deleteUser(id)
    }

    override suspend fun getUserDetailByUsername(username: String): User {
        return userDao.getUserDetailByUsername(username)
    }

    override suspend fun isUserExists(username: String, password: String): Boolean {
        return userDao.isUserExists(username, password) > 0
    }

    override fun getAllUsers(): Flow<List<User>> {
        return userDao.getAllUsers()
    }

    override fun getAllUsersByRole(role: String): Flow<List<User>> {
        return userDao.getAllUsersByRole(role)
    }
}