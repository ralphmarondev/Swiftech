package com.ralphmarondev.swiftech.core.domain.model

data class QuestionReport(
    val questionId: Int,
    val questionText: String,
    val answers: List<String> // raw answers, could be "5", "4", etc. (depends on how you saved them)
)
