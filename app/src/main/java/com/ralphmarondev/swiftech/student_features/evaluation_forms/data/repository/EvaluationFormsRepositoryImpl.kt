package com.ralphmarondev.swiftech.student_features.evaluation_forms.data.repository

import com.ralphmarondev.swiftech.core.data.local.database.sfdao.SFEvaluationFormsDao
import com.ralphmarondev.swiftech.core.domain.model.EvaluationForm
import com.ralphmarondev.swiftech.student_features.evaluation_forms.domain.repository.EvaluationFormsRepository

class EvaluationFormsRepositoryImpl(
    private val dao: SFEvaluationFormsDao
) : EvaluationFormsRepository {
    override suspend fun getEvaluationFormsByTerm(term: String): List<EvaluationForm> {
        TODO("Not yet implemented")
    }
}