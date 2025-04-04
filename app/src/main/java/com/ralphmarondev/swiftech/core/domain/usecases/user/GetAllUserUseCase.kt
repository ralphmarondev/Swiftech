package com.ralphmarondev.swiftech.core.domain.usecases.user

import com.ralphmarondev.swiftech.core.domain.model.User
import com.ralphmarondev.swiftech.core.domain.repositories.UserRepository
import kotlinx.coroutines.flow.Flow

class GetAllUserUseCase(
    private val repository: UserRepository
) {
    operator fun invoke(): Flow<List<User>> {
        return repository.getAllUsers()
    }
}