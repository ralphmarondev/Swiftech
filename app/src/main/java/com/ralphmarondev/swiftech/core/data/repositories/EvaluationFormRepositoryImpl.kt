package com.ralphmarondev.swiftech.core.data.repositories

import com.ralphmarondev.swiftech.core.data.local.database.dao.EvaluationFormDao
import com.ralphmarondev.swiftech.core.domain.model.EvaluationForm
import com.ralphmarondev.swiftech.core.domain.repositories.EvaluationFormRepository
import kotlinx.coroutines.flow.Flow

class EvaluationFormRepositoryImpl(
    private val evaluationFormDao: EvaluationFormDao
) : EvaluationFormRepository {

    override suspend fun createEvaluationForm(evaluationForm: EvaluationForm) {
        evaluationFormDao.createEvaluationForm(evaluationForm)
    }

    override fun getAllEvaluationForms(): Flow<List<EvaluationForm>> {
        return evaluationFormDao.getAllEvaluationForms()
    }

    override suspend fun getEvaluationFormById(id: Int): EvaluationForm? {
        return evaluationFormDao.getEvaluationFormById(id)
    }
}