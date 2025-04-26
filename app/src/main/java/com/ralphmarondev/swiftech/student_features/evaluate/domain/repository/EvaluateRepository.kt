package com.ralphmarondev.swiftech.student_features.evaluate.domain.repository

import com.ralphmarondev.swiftech.core.domain.model.EvaluationForm
import com.ralphmarondev.swiftech.core.domain.model.EvaluationQuestion
import kotlinx.coroutines.flow.Flow

interface EvaluateRepository {
    suspend fun getEvaluationFormDetailById(formId: Int): EvaluationForm
    fun getEvaluationFormQuestionsById(formId: Int): Flow<List<EvaluationQuestion>>
}