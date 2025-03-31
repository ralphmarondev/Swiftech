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

    @Query("DELETE FROM user WHERE id = :id")
    suspend fun deleteUser(id: Int)

    @Query("SELECT * FROM user WHERE username = :username LIMIT 1")
    suspend fun getUserDetailByUsername(username: String): User

    @Query("SELECT COUNT(*) FROM user where username = :username AND password = :password")
    suspend fun isUserExists(username: String, password: String): Int

    @Query("SELECT * FROM user")
    fun getAllUsers(): Flow<List<User>>

    @Query("SELECT * FROM user WHERE role = :role")
    fun getAllUsersByRole(role: String): Flow<List<User>>
}