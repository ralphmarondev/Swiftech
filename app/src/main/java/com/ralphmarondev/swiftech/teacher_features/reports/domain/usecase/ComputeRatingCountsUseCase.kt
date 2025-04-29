package com.ralphmarondev.swiftech.teacher_features.reports.domain.usecase

import com.ralphmarondev.swiftech.admin_features.reports.domain.model.RatingCounts
import com.ralphmarondev.swiftech.admin_features.reports.domain.repository.ReportRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ComputeRatingCountsUseCase(
    private val repository: ReportRepository
) {
    operator fun invoke(courseId: Int): Flow<RatingCounts> {
        return repository.getEvaluationAnswersByCourse(courseId)
            .map { answers ->
                answers.fold(RatingCounts()) { acc, answer ->
                    when (answer.answer.lowercase()) {
                        "excellent" -> acc.copy(excellent = acc.excellent + 1)
                        "very good" -> acc.copy(veryGood = acc.veryGood + 1)
                        "good" -> acc.copy(good = acc.good + 1)
                        "fair" -> acc.copy(fair = acc.fair + 1)
                        "poor" -> acc.copy(poor = acc.poor + 1)
                        else -> acc
                    }
                }
            }
    }
}