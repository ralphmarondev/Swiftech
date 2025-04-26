package com.ralphmarondev.swiftech.student_features.evaluate.domain.usecase

import com.ralphmarondev.swiftech.student_features.evaluate.domain.repository.EvaluateRepository

class GetEvaluationFormDetailByIdUseCase(
    private val repository: EvaluateRepository
) {
    suspend operator fun invoke(formId: Int) = repository.getEvaluationFormDetailById(formId)
}