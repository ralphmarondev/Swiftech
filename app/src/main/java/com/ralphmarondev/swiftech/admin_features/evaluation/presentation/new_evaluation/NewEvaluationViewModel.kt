package com.ralphmarondev.swiftech.admin_features.evaluation.presentation.new_evaluation

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

    private val _term = MutableStateFlow("")
    val term = _term.asStateFlow()

    private val _description = MutableStateFlow("")
    val description = _description.asStateFlow()

    private val _questions = MutableStateFlow<List<String>>(emptyList())
    val questions = _questions.asStateFlow()

    private val _newQuestion = MutableStateFlow("")
    val newQuestion = _newQuestion.asStateFlow()

    private val _showNewQuestionDialog = MutableStateFlow(false)
    val showNewQuestionDialog = _showNewQuestionDialog.asStateFlow()

    private val _showUpdateQuestionDialog = MutableStateFlow(false)
    val showUpdateQuestionDialog = _showUpdateQuestionDialog.asStateFlow()

    private val _showDeleteQuestionDialog = MutableStateFlow(false)
    val showDeleteQuestionDialog = _showDeleteQuestionDialog.asStateFlow()

    private val _selectedQuestion = MutableStateFlow("")
    val selectedQuestion = _selectedQuestion.asStateFlow()

    private val _formResponse = MutableStateFlow<Result?>(null)
    val formResponse = _formResponse.asStateFlow()

    private val _questionResponse = MutableStateFlow<Result?>(null)
    val questionResponse = _questionResponse.asStateFlow()

    private val _showSaveEvaluationDialog = MutableStateFlow(false)
    val showSaveEvaluationDialog = _showSaveEvaluationDialog.asStateFlow()

    private val _showEvaluationResultDialog = MutableStateFlow(false)
    val showEvaluationResultDialog = _showEvaluationResultDialog.asStateFlow()

    private val _shouldNavigateBack = MutableStateFlow(false)
    val shouldNavigateBack = _shouldNavigateBack.asStateFlow()


    fun onTitleValueChange(value: String) {
        _title.value = value
    }

    fun onDescriptionValueChange(value: String) {
        _description.value = value
    }

    fun onTermValueChange(value: String) {
        _term.value = value
    }

    fun onNewQuestionValueChange(value: String) {
        _newQuestion.value = value
    }

    fun setShowNewQuestionDialog() {
        _showNewQuestionDialog.value = !_showNewQuestionDialog.value
    }

    fun onConfirm() {
        viewModelScope.launch {
            val question = newQuestion.value.trim()

            if (question.isEmpty()) {
                _questionResponse.value = Result(
                    success = false,
                    message = "Question cannot be empty"
                )
                return@launch
            }
            _questions.value += question
            _newQuestion.value = ""

            setShowNewQuestionDialog()
            _questionResponse.value = Result(
                success = true,
                message = "Question added"
            )
        }
    }

    fun onSave() {
        viewModelScope.launch {
            val title = _title.value.trim()
            val term = _term.value.trim()
            val description = _description.value.trim()

            if (title.isEmpty() && description.isEmpty() && term.isEmpty()) {
                _formResponse.value = Result(
                    success = false,
                    message = "Please fill in all fields."
                )
                return@launch
            }

            if (title.isEmpty()) {
                _formResponse.value = Result(
                    success = false,
                    message = "Title cannot be empty."
                )
                return@launch
            }

            if (description.isEmpty()) {
                _formResponse.value = Result(
                    success = false,
                    message = "Description cannot be empty."
                )
                return@launch
            }

            if (term.isEmpty()) {
                _formResponse.value = Result(
                    success = false,
                    message = "Term cannot be empty."
                )
                return@launch
            }

            try {
                val id = createEvaluationFormUseCase(
                    EvaluationForm(
                        title = title,
                        term = term,
                        description = description
                    )
                )

                if (id == 0) {
                    _formResponse.value = Result(
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
                            evaluationFormId = id
                        )
                    )
                }

                _formResponse.value = Result(
                    success = true,
                    message = "Evaluation form created successfully."
                )
                _shouldNavigateBack.value = true
            } catch (e: Exception) {
                Log.e("App", "Error creating evaluation form: ${e.message}")
                _formResponse.value = Result(
                    success = false,
                    message = "Error creating evaluation form"
                )
            }
        }
    }

    fun setShowSaveEvaluationDialog(value: Boolean) {
        _showSaveEvaluationDialog.value = value
    }

    fun setShowEvaluationResultDialog(value: Boolean) {
        _showEvaluationResultDialog.value = value
    }

    fun setShowUpdateQuestionDialog(value: Boolean) {
        _showUpdateQuestionDialog.value = value
    }

    fun setShowDeleteQuestionDIalog(value: Boolean) {
        _showDeleteQuestionDialog.value = value
    }

    fun setSelectedQuestion(value: String) {
        _selectedQuestion.value = value
    }

    fun updateSelectedQuestion(value: String) {
        val currentQuestions = _questions.value.toMutableList()
        val selected = _selectedQuestion.value.trim()

        val index = currentQuestions.indexOf(selected)
        if (index != -1) {
            currentQuestions[index] = value
            _questions.value = currentQuestions
            _selectedQuestion.value = value
        }
        setShowUpdateQuestionDialog(false)
    }

    fun deleteSelectedQuestion() {
        val currentQuestions = _questions.value.toMutableList()
        val selected = _selectedQuestion.value

        if (currentQuestions.remove(selected)) {
            _questions.value = currentQuestions
            _selectedQuestion.value = ""

            setShowDeleteQuestionDIalog(false)
        }
    }
}