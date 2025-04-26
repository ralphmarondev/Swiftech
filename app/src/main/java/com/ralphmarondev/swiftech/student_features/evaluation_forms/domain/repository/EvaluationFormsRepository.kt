package com.ralphmarondev.swiftech.student_features.evaluation_forms.domain.repository

import com.ralphmarondev.swiftech.core.domain.model.EvaluationForm

interface EvaluationFormsRepository {
    suspend fun getEvaluationFormsByTerm(term: String): List<EvaluationForm>
}