package com.ralphmarondev.swiftech.student_features.evaluate.domain.repository

import com.ralphmarondev.swiftech.core.domain.model.EvaluationForm
import com.ralphmarondev.swiftech.core.domain.model.EvaluationQuestion

interface EvaluateRepository {
    suspend fun getEvaluationFormDetailById(formId: Int): EvaluationForm
    suspend fun getEvaluationFormQuestionsById(formId: Int): List<EvaluationQuestion>
}