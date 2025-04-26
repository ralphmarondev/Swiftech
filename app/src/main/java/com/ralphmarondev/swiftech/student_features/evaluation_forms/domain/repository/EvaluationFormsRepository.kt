package com.ralphmarondev.swiftech.student_features.evaluation_forms.domain.repository

import com.ralphmarondev.swiftech.core.domain.model.EvaluationForm
import kotlinx.coroutines.flow.Flow

interface EvaluationFormsRepository {
    fun getEvaluationFormsByTerm(term: String): Flow<List<EvaluationForm>>
}