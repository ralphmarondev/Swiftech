package com.ralphmarondev.swiftech.core.data.local.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.ralphmarondev.swiftech.core.domain.model.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Upsert
    suspend fun createUser(user: User)

    @Update
    suspend fun updateUser(user: User)

    @Query("UPDATE user SET isDeleted = 1 WHERE id = :id")
    suspend fun deleteUser(id: Int)

    @Query("SELECT * FROM user WHERE username = :username AND isDeleted = 0 LIMIT 1")
    suspend fun getUserDetailByUsername(username: String): User

    @Query("SELECT COUNT(*) FROM user where username = :username AND password = :password AND isDeleted = 0")
    suspend fun isUserExists(username: String, password: String): Int

    @Query("SELECT * FROM user WHERE isDeleted = 0")
    fun getAllUsers(): Flow<List<User>>

    @Query("SELECT * FROM user WHERE role = :role AND isDeleted = 0")
    fun getAllUsersByRole(role: String): Flow<List<User>>

    @Query("SELECT * FROM user WHERE id = :id")
    suspend fun getUserById(id: Int): User?
}