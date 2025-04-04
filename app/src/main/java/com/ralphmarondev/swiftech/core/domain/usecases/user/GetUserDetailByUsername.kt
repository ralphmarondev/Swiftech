package com.ralphmarondev.swiftech.core.domain.usecases.user

import android.util.Log
import com.ralphmarondev.swiftech.core.domain.model.User
import com.ralphmarondev.swiftech.core.domain.repositories.UserRepository

class GetUserDetailByUsername(
    private val repository: UserRepository
) {
    suspend operator fun invoke(username: String): User {
        return try {
            val result = repository.getUserDetailByUsername(username)
            Log.d("App", "Success getting user detail")
            result
        } catch (e: Exception) {
            Log.e("App", "Error getting user detail")
            User(
                id = -1,
                username = "error",
                password = "error",
                role = "error",
                fullName = "error"
            )
        }
    }
}