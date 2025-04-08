package com.ralphmarondev.swiftech.features.evaluation.presentation.new_evaluation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ralphmarondev.swiftech.core.domain.model.EvaluationForm
import com.ralphmarondev.swiftech.core.domain.model.EvaluationQuestion
import com.ralphmarondev.swiftech.core.domain.model.Result
import com.ralphmarondev.swiftech.core.domain.usecases.evaluation.CreateEvaluationFormUseCase
import com.ralphmarondev.swiftech.core.domain.usecases.evaluation.SaveQuestionToEvaluationFormUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NewEvaluationViewModel(
    private val createEvaluationFormUseCase: CreateEvaluationFormUseCase,
    private val saveQuestionToEvaluationFormUseCase: SaveQuestionToEvaluationFormUseCase
) : ViewModel() {

    private val _title = MutableStateFlow("")
    val title = _title.asStateFlow()

    private val _description = MutableStateFlow("")
    val description = _description.asStateFlow()

    private val _questions = MutableStateFlow<List<String>>(emptyList())
    val questions = _questions.asStateFlow()

    private val _newQuestion = MutableStateFlow("")
    val newQuestion = _newQuestion.asStateFlow()

    private val _showNewQuestionDialog = MutableStateFlow(false)
    val showNewQuestionDialog = _showNewQuestionDialog.asStateFlow()

    private val _response = MutableStateFlow<Result?>(null)
    val response = _response.asStateFlow()


    fun onTitleValueChange(value: String) {
        _title.value = value
    }

    fun onDescriptionValueChange(value: String) {
        _description.value = value
    }

    fun onNewQuestionValueChange(value: String) {
        _newQuestion.value = value
    }

    fun setShowNewQuestionDialog() {
        _showNewQuestionDialog.value = !_showNewQuestionDialog.value
    }

    fun onConfirm() {
        viewModelScope.launch {
            val question = newQuestion.value

            if (question.isEmpty()) {
                _response.value = Result(
                    success = false,
                    message = "Question cannot be empty"
                )
                return@launch
            }
            _response.value = Result(
                success = true,
                message = "Question added"
            )
            _questions.value += question
            _newQuestion.value = ""
            setShowNewQuestionDialog()
        }
    }

    fun onSave() {
        viewModelScope.launch {
            val title = _title.value.trim()
            val description = _description.value.trim()

            if (title.isEmpty() && description.isEmpty()) {
                _response.value = Result(
                    success = false,
                    message = "Title and description cannot be empty"
                )
                return@launch
            }

            if (title.isEmpty()) {
                _response.value = Result(
                    success = false,
                    message = "Title cannot be empty"
                )
                return@launch
            }

            if (description.isEmpty()) {
                _response.value = Result(
                    success = false,
                    message = "Description cannot be empty"
                )
                return@launch
            }

            try {
                val form = createEvaluationFormUseCase(
                    EvaluationForm(
                        title = title,
                        description = description
                    )
                )

                Log.d("App", "Form title: ${form.title}, id: ${form.id}")

                if (form.id == 0) {
                    _response.value = Result(
                        success = false,
                        message = "Error: Failed to create evaluation form."
                    )
                    Log.e("App", "Error: Failed to create evaluation form.")
                    return@launch
                }

                _questions.value.forEach {
                    saveQuestionToEvaluationFormUseCase(
                        question = EvaluationQuestion(
                            questionText = it,
                            evaluationFormId = form.id
                        )
                    )
                }

                _response.value = Result(
                    success = true,
                    message = "Evaluation form created"
                )
            } catch (e: Exception) {
                Log.e("App", "Error creating evaluation form: ${e.message}")
                _response.value = Result(
                    success = false,
                    message = "Error creating evaluation form"
                )
            }
        }
    }
}