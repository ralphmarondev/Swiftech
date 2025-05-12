package com.ralphmarondev.swiftech.admin_features.reports.domain.model

data class StudentReport(
    val studentId: Int,
    val studentName: String,
    val ratingCounts: RatingCounts
)
