package com.ralphmarondev.swiftech.student_features.evaluate.domain.usecase

import com.ralphmarondev.swiftech.core.domain.model.EvaluationAnswer
import com.ralphmarondev.swiftech.core.domain.model.EvaluationResponse
import com.ralphmarondev.swiftech.student_features.evaluate.domain.repository.EvaluateRepository

class SubmitEvaluationUseCase(
    private val repository: EvaluateRepository
) {
    suspend operator fun invoke(response: EvaluationResponse, answers: List<EvaluationAnswer>) {
        val alreadyEvaluated = repository.hasStudentAlreadyEvaluated(
            studentId = response.studentId,
            courseId = response.courseId,
            evaluationFormId = response.evaluationFormId
        )
        if (alreadyEvaluated) {
            throw IllegalStateException("Student has already evaluated this form.")
        }
        repository.submitEvaluation(response, answers)
    }
}