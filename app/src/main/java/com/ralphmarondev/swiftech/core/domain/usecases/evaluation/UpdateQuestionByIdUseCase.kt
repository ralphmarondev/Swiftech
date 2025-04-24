package com.ralphmarondev.swiftech.core.domain.usecases.evaluation

import com.ralphmarondev.swiftech.core.domain.model.EvaluationQuestion
import com.ralphmarondev.swiftech.core.domain.repositories.EvaluationFormRepository

class UpdateQuestionByIdUseCase(
    private val repository: EvaluationFormRepository
) {
    suspend operator fun invoke(question: EvaluationQuestion) =
        repository.updateQuestionById(question)
}