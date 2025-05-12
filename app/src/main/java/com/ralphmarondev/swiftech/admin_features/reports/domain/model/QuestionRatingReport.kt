package com.ralphmarondev.swiftech.admin_features.reports.domain.model

data class QuestionRatingReport(
    val questionId: Int,
    val questionText: String,
    val ratingCounts: RatingCounts
)
