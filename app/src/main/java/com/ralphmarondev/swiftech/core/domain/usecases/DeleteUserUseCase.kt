package com.ralphmarondev.swiftech.core.domain.usecases

import android.util.Log
import com.ralphmarondev.swiftech.core.domain.repositories.UserRepository

class DeleteUserUseCase(
    private val repository: UserRepository
) {
    suspend operator fun invoke(id: Int) {
        try {
            repository.deleteUser(id)
            Log.d("App", "User deleted successfully!")
        } catch (e: Exception) {
            Log.e("App", "Error deleting user: ${e.message}")
        }
    }
}