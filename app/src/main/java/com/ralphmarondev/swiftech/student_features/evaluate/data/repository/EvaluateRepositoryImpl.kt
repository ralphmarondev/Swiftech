package com.ralphmarondev.swiftech.student_features.evaluate.data.repository

import com.ralphmarondev.swiftech.core.data.local.database.dao.EvaluationFormDao
import com.ralphmarondev.swiftech.core.domain.model.EvaluationForm
import com.ralphmarondev.swiftech.core.domain.model.EvaluationQuestion
import com.ralphmarondev.swiftech.student_features.evaluate.domain.repository.EvaluateRepository

class EvaluateRepositoryImpl(
    private val dao: EvaluationFormDao
) : EvaluateRepository {
    override suspend fun getEvaluationFormDetailById(formId: Int): EvaluationForm {
        TODO("Not yet implemented")
    }

    override suspend fun getEvaluationFormQuestionsById(formId: Int): List<EvaluationQuestion> {
        TODO("Not yet implemented")
    }
}