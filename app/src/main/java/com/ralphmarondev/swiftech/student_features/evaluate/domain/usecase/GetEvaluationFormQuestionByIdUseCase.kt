package com.ralphmarondev.swiftech.student_features.evaluate.domain.usecase

import com.ralphmarondev.swiftech.student_features.evaluate.domain.repository.EvaluateRepository

class GetEvaluationFormQuestionByIdUseCase(
    private val repository: EvaluateRepository
) {
    operator fun invoke(formId: Int) = repository.getEvaluationFormQuestionsById(formId)
}