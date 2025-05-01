package com.ralphmarondev.swiftech.core.domain.usecases.evaluation

import com.ralphmarondev.swiftech.core.domain.repositories.EvaluationFormRepository

class DeleteEvaluationFormByIdUseCase(
    private val repository: EvaluationFormRepository
) {
    suspend operator fun invoke(evaluationFormId: Int) =
        repository.deleteEvaluationForm(evaluationFormId)
}