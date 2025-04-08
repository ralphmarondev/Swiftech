package com.ralphmarondev.swiftech.core.domain.usecases.evaluation

import com.ralphmarondev.swiftech.core.domain.model.EvaluationQuestion
import com.ralphmarondev.swiftech.core.domain.repositories.EvaluationFormRepository

class SaveQuestionToEvaluationFormUseCase(
    private val repository: EvaluationFormRepository
) {
    suspend operator fun invoke(question: EvaluationQuestion) {
        repository.saveQuestionToEvaluationForms(question)
    }
}