package com.ralphmarondev.swiftech.core.domain.usecases

import android.util.Log
import com.ralphmarondev.swiftech.core.domain.model.User
import com.ralphmarondev.swiftech.core.domain.repositories.UserRepository

class UpdateUserUseCase(
    private val repository: UserRepository
) {
    suspend operator fun invoke(user: User) {
        try {
            repository.updateUser(user)
            Log.d("App", "User updated successfully!")
        } catch (e: Exception) {
            Log.e("App", "Error updating user: ${e.message}")
        }
    }
}