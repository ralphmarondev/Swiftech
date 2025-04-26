package com.ralphmarondev.swiftech.student_features.evaluation_forms.data.repository

import com.ralphmarondev.swiftech.core.data.local.database.dao.EvaluationFormDao
import com.ralphmarondev.swiftech.core.domain.model.EvaluationForm
import com.ralphmarondev.swiftech.student_features.evaluation_forms.domain.repository.EvaluationFormsRepository
import kotlinx.coroutines.flow.Flow

class EvaluationFormsRepositoryImpl(
    private val dao: EvaluationFormDao
) : EvaluationFormsRepository {
    override fun getEvaluationFormsByTerm(term: String): Flow<List<EvaluationForm>> {
        return dao.getEvaluationFormsByTerm(term)
    }
}