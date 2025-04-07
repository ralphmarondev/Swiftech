package com.ralphmarondev.swiftech.core.domain.usecases.evaluation

import com.ralphmarondev.swiftech.core.domain.model.EvaluationForm
import com.ralphmarondev.swiftech.core.domain.repositories.EvaluationFormRepository
import kotlinx.coroutines.flow.Flow

class GetAllEvaluationFormsUseCase(
    private val repository: EvaluationFormRepository
) {
    operator fun invoke(): Flow<List<EvaluationForm>> = repository.getAllEvaluationForms()
}