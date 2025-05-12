package com.ralphmarondev.swiftech.admin_features.reports.domain.usecase

import com.ralphmarondev.swiftech.admin_features.reports.domain.model.RatingCounts
import com.ralphmarondev.swiftech.admin_features.reports.domain.model.StudentReport
import com.ralphmarondev.swiftech.core.data.local.database.dao.EvaluationFormDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetStudentRatingReportsByCourseUseCase(
    private val dao: EvaluationFormDao
) {
    operator fun invoke(courseId: Int): Flow<List<StudentReport>> {
        return dao.getEvaluationAnswersGroupedByStudent(courseId)
            .map { dtoList ->
                dtoList
                    .groupBy { it.studentId to it.studentName }
                    .map { (studentPair, answersList) ->
                        val counts = answersList.fold(RatingCounts()) { acc, dto ->
                            when (dto.answer.lowercase().trim()) {
                                "excellent" -> acc.copy(excellent = acc.excellent + 1)
                                "very good" -> acc.copy(veryGood = acc.veryGood + 1)
                                "good" -> acc.copy(good = acc.good + 1)
                                "fair" -> acc.copy(fair = acc.fair + 1)
                                "poor" -> acc.copy(poor = acc.poor + 1)
                                else -> acc
                            }
                        }

                        StudentReport(
                            studentId = studentPair.first,
                            studentName = studentPair.second,
                            ratingCounts = counts
                        )
                    }
            }
    }
}

