package com.ralphmarondev.swiftech.teacher_features.reports.domain.repository

import com.ralphmarondev.swiftech.core.domain.model.EvaluationAnswer
import kotlinx.coroutines.flow.Flow

interface ReportRepository {
    fun getEvaluationAnswersByCourse(courseId: Int): Flow<List<EvaluationAnswer>>
}