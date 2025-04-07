package com.ralphmarondev.swiftech.core.domain.usecases.evaluation

import com.ralphmarondev.swiftech.core.domain.repositories.EvaluationFormRepository

class GetEvaluationFormByIdUseCase(
    private val repository: EvaluationFormRepository
) {
    suspend operator fun invoke(id: Int) = repository.getEvaluationFormById(id)
}