package com.ralphmarondev.swiftech.core.domain.usecases.evaluation

import android.util.Log
import com.ralphmarondev.swiftech.core.domain.model.EvaluationForm
import com.ralphmarondev.swiftech.core.domain.repositories.EvaluationFormRepository

class CreateEvaluationFormUseCase(
    private val repository: EvaluationFormRepository
) {
    suspend operator fun invoke(evaluationForm: EvaluationForm) {
        try {
            repository.createEvaluationForm(evaluationForm)
            Log.d(
                "App",
                "Evaluation form with title: ${evaluationForm.title} created successfully."
            )
        } catch (e: Exception) {
            Log.e("App", "Error creating evaluation form.")
        }
    }
}