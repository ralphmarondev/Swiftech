package com.ralphmarondev.swiftech.core.domain.usecases.evaluation

import com.ralphmarondev.swiftech.core.domain.model.EvaluationForm
import com.ralphmarondev.swiftech.core.domain.repositories.EvaluationFormRepository

class UpdateEvaluationFormUseCase(
    private val repository: EvaluationFormRepository
) {
    suspend operator fun invoke(evaluationForm: EvaluationForm) =
        repository.updateEvaluationForm(evaluationForm)
}