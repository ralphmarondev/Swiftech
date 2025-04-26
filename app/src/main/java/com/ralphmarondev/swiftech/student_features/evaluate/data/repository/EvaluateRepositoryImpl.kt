package com.ralphmarondev.swiftech.student_features.evaluate.data.repository

import com.ralphmarondev.swiftech.core.data.local.database.dao.EvaluationFormDao
import com.ralphmarondev.swiftech.core.domain.model.EvaluationForm
import com.ralphmarondev.swiftech.core.domain.model.EvaluationQuestion
import com.ralphmarondev.swiftech.student_features.evaluate.domain.repository.EvaluateRepository
import kotlinx.coroutines.flow.Flow

class EvaluateRepositoryImpl(
    private val dao: EvaluationFormDao
) : EvaluateRepository {
    override suspend fun getEvaluationFormDetailById(formId: Int): EvaluationForm {
        return dao.getEvaluationFormDetailById(formId)
    }

    override fun getEvaluationFormQuestionsById(formId: Int): Flow<List<EvaluationQuestion>> {
        return dao.getEvaluationFormQuestionsById(formId)
    }
}