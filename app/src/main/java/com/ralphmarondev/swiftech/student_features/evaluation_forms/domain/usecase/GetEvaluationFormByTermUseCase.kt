package com.ralphmarondev.swiftech.student_features.evaluation_forms.domain.usecase

import com.ralphmarondev.swiftech.student_features.evaluation_forms.domain.repository.EvaluationFormsRepository

class GetEvaluationFormByTermUseCase(
    private val repository: EvaluationFormsRepository
) {
    suspend operator fun invoke(term: String) = repository.getEvaluationFormsByTerm(term)
}