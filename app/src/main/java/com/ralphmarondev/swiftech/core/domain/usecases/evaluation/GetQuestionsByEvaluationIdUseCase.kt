package com.ralphmarondev.swiftech.core.domain.usecases.evaluation

import com.ralphmarondev.swiftech.core.domain.repositories.EvaluationFormRepository

class GetQuestionsByEvaluationIdUseCase(
    private val repository: EvaluationFormRepository
) {
    operator fun invoke(id: Int) = repository.getQuestionsByEvaluationId(id)
}