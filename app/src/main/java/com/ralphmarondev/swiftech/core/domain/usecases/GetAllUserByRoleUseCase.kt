package com.ralphmarondev.swiftech.core.domain.usecases

import com.ralphmarondev.swiftech.core.domain.model.User
import com.ralphmarondev.swiftech.core.domain.repositories.UserRepository
import kotlinx.coroutines.flow.Flow

class GetAllUserByRoleUseCase(
    private val repository: UserRepository
) {
    operator fun invoke(role: String): Flow<List<User>> {
        return repository.getAllUsersByRole(role)
    }
}