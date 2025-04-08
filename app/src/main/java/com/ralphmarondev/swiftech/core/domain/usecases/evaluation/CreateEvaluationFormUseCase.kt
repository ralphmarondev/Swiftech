package com.ralphmarondev.swiftech.core.domain.usecases.evaluation

import android.util.Log
import com.ralphmarondev.swiftech.core.domain.model.EvaluationForm
import com.ralphmarondev.swiftech.core.domain.repositories.EvaluationFormRepository

class CreateEvaluationFormUseCase(
    private val repository: EvaluationFormRepository
) {
    suspend operator fun invoke(evaluationForm: EvaluationForm): EvaluationForm {
        try {
            repository.createEvaluationForm(evaluationForm)
            Log.d(
                "App",
                "Evaluation form with title: ${evaluationForm.title} created successfully."
            )
            Log.d(
                "App",
                "From evaluation form use case, last inserted row id: ${repository.getLastInsertedId()}"
            )
            return evaluationForm.copy(id = repository.getLastInsertedId().toInt())
        } catch (e: Exception) {
            Log.e("App", "Error creating evaluation form.")
            throw e
        }
    }
}