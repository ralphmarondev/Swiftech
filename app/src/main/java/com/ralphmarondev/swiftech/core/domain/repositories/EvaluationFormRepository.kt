package com.ralphmarondev.swiftech.core.domain.repositories

import com.ralphmarondev.swiftech.core.domain.model.EvaluationForm
import com.ralphmarondev.swiftech.core.domain.model.EvaluationQuestion
import kotlinx.coroutines.flow.Flow

interface EvaluationFormRepository {

    suspend fun createEvaluationForm(evaluationForm: EvaluationForm)

    fun getAllEvaluationForms(): Flow<List<EvaluationForm>>

    suspend fun getEvaluationFormById(id: Int): EvaluationForm?

    suspend fun saveQuestionToEvaluationForms(question: EvaluationQuestion)

    suspend fun getLastInsertedId(): Long
}