package com.ralphmarondev.swiftech.core.domain.usecases

import android.util.Log
import com.ralphmarondev.swiftech.core.domain.model.User
import com.ralphmarondev.swiftech.core.domain.repositories.UserRepository

class CreateUserUseCase(
    private val repository: UserRepository
) {
    suspend operator fun invoke(user: User) {
        try {
            repository.createUser(user)
            Log.d("App", "User created successfully!")
        } catch (e: Exception) {
            Log.e("App", "Error creating user: ${e.message}")
        }
    }
}