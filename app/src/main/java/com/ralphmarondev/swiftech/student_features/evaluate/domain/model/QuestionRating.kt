package com.ralphmarondev.swiftech.student_features.evaluate.domain.model

data class QuestionRating (
    val question: String,
    val selectedRating: String? = null
)