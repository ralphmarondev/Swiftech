package com.ralphmarondev.swiftech.core.domain.repositories

import com.ralphmarondev.swiftech.core.domain.model.EvaluationForm
import kotlinx.coroutines.flow.Flow

interface EvaluationFormRepository {

    suspend fun createEvaluationForm(evaluationForm: EvaluationForm)

    fun getAllEvaluationForms(): Flow<List<EvaluationForm>>
}