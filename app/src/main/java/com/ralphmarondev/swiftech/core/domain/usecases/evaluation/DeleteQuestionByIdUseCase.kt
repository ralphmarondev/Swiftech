package com.ralphmarondev.swiftech.core.domain.usecases.evaluation

import com.ralphmarondev.swiftech.core.domain.repositories.EvaluationFormRepository

class DeleteQuestionByIdUseCase(
    private val repository: EvaluationFormRepository
) {
    suspend operator fun invoke(id: Int) = repository.deleteQuestionById(id)
}