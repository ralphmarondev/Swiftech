package com.ralphmarondev.swiftech.student_features.evaluate.domain.usecase

import com.ralphmarondev.swiftech.student_features.evaluate.domain.repository.EvaluateRepository

class HasEvaluatedUseCase(
    private val repository: EvaluateRepository
) {
    suspend operator fun invoke(
        studentId: Int,
        courseId: Int,
        evaluationFormId: Int
    ) = repository.hasStudentAlreadyEvaluated(
        studentId = studentId,
        courseId = courseId,
        evaluationFormId = evaluationFormId
    )
}