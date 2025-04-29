package com.ralphmarondev.swiftech.admin_features.reports.domain.usecase

import com.ralphmarondev.swiftech.admin_features.reports.domain.repository.ReportRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ComputeAverageRatingUseCase(
    private val repository: ReportRepository
) {
    operator fun invoke(courseId: Int): Flow<Double> {
        return repository.getEvaluationAnswersByCourse(courseId)
            .map { answers ->
                val ratings = answers.map { mapAnswerToRating(it.answer) }.filter { it > 0 }
                if (ratings.isNotEmpty()) ratings.average() else 0.0
            }
    }

    private fun mapAnswerToRating(answer: String): Int {
        return when (answer.lowercase()) {
            "excellent" -> 5
            "very good" -> 4
            "good" -> 3
            "fair" -> 2
            "poor" -> 1
            else -> 0
        }
    }
}