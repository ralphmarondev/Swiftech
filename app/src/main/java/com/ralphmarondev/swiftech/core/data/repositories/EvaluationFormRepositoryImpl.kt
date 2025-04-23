package com.ralphmarondev.swiftech.core.data.repositories

import com.ralphmarondev.swiftech.core.data.local.database.dao.EvaluationFormDao
import com.ralphmarondev.swiftech.core.domain.model.EvaluationForm
import com.ralphmarondev.swiftech.core.domain.model.EvaluationQuestion
import com.ralphmarondev.swiftech.core.domain.repositories.EvaluationFormRepository
import kotlinx.coroutines.flow.Flow

class EvaluationFormRepositoryImpl(
    private val evaluationFormDao: EvaluationFormDao
) : EvaluationFormRepository {

    override suspend fun createEvaluationForm(evaluationForm: EvaluationForm): Int {
        return evaluationFormDao.createEvaluationForm(evaluationForm).toInt()
    }

    override fun getAllEvaluationForms(): Flow<List<EvaluationForm>> {
        return evaluationFormDao.getAllEvaluationForms()
    }

    override suspend fun getEvaluationFormById(id: Int): EvaluationForm? {
        return evaluationFormDao.getEvaluationFormById(id)
    }

    override suspend fun saveQuestionToEvaluationForms(question: EvaluationQuestion) {
        evaluationFormDao.saveQuestionToEvaluationForm(question)
    }

    override suspend fun getLastInsertedId(): Long {
        return evaluationFormDao.getLastInsertedId()
    }
}