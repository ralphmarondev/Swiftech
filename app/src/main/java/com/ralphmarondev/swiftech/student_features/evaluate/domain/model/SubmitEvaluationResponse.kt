package com.ralphmarondev.swiftech.student_features.evaluate.domain.model

data class SubmitEvaluationResponse(
    val formId: Int,
    val studentId: Int,
    val courseId: Int,
    val answers: List<SubmitEvaluationAnswer>
)
