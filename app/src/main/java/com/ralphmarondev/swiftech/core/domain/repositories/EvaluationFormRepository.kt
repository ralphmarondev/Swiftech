package com.ralphmarondev.swiftech.core.domain.repositories

import com.ralphmarondev.swiftech.core.domain.model.EvaluationForm
import com.ralphmarondev.swiftech.core.domain.model.EvaluationQuestion
import kotlinx.coroutines.flow.Flow

interface EvaluationFormRepository {

    suspend fun createEvaluationForm(evaluationForm: EvaluationForm): Int

    suspend fun updateEvaluationForm(evaluationForm: EvaluationForm)

    suspend fun deleteEvaluationForm(evaluationFormId: Int)

    fun getAllEvaluationForms(): Flow<List<EvaluationForm>>

    suspend fun getEvaluationFormById(id: Int): EvaluationForm?

    suspend fun saveQuestionToEvaluationForms(question: EvaluationQuestion)

    suspend fun getLastInsertedId(): Long

    fun getQuestionsByEvaluationId(id: Int): Flow<List<EvaluationQuestion>>

    suspend fun deleteQuestionById(id: Int)

    suspend fun updateQuestionById(question: EvaluationQuestion)

    suspend fun hasAnyoneAnsweredTheEvaluationForm(evaluationFormId: Int): Boolean
}