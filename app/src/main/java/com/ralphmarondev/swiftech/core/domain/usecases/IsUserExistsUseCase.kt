package com.ralphmarondev.swiftech.core.domain.usecases

import android.util.Log
import com.ralphmarondev.swiftech.core.domain.repositories.UserRepository

class IsUserExistsUseCase(
    private val repository: UserRepository
) {
    suspend operator fun invoke(username: String, password: String): Boolean {
        return try {
            repository.isUserExists(username, password)
        } catch (e: Exception) {
            Log.e("App", "IsUserExists error: ${e.message}")
            false
        }
    }
}