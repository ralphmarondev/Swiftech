package com.ralphmarondev.swiftech.admin_features.reports.data.repository

import com.ralphmarondev.swiftech.admin_features.reports.domain.repository.ReportRepository
import com.ralphmarondev.swiftech.core.data.local.database.dao.EvaluationFormDao
import com.ralphmarondev.swiftech.core.domain.model.EvaluationAnswer
import kotlinx.coroutines.flow.Flow

class ReportRepositoryImpl(
    private val dao: EvaluationFormDao
) : ReportRepository {
    override fun getEvaluationAnswersByCourse(courseId: Int): Flow<List<EvaluationAnswer>> {
        return dao.getEvaluationAnswersByCourse(courseId)
    }
}