package com.ralphmarondev.swiftech.admin_features.evaluation.presentation.evaluation_detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ralphmarondev.swiftech.core.domain.model.EvaluationForm
import com.ralphmarondev.swiftech.core.domain.model.EvaluationQuestion
import com.ralphmarondev.swiftech.core.domain.model.Result
import com.ralphmarondev.swiftech.core.domain.usecases.evaluation.DeleteEvaluationFormByIdUseCase
import com.ralphmarondev.swiftech.core.domain.usecases.evaluation.DeleteQuestionByIdUseCase
import com.ralphmarondev.swiftech.core.domain.usecases.evaluation.GetEvaluationFormByIdUseCase
import com.ralphmarondev.swiftech.core.domain.usecases.evaluation.GetQuestionsByEvaluationIdUseCase
import com.ralphmarondev.swiftech.core.domain.usecases.evaluation.SaveQuestionToEvaluationFormUseCase
import com.ralphmarondev.swiftech.core.domain.usecases.evaluation.UpdateQuestionByIdUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class EvaluationDetailViewModel(
    private val evaluationId: Int,
    private val getEvaluationFormByIdUseCase: GetEvaluationFormByIdUseCase,
    private val getQuestionsByEvaluationIdUseCase: GetQuestionsByEvaluationIdUseCase,
    private val deleteEvaluationFormByIdUseCase: DeleteEvaluationFormByIdUseCase,
    private val updateQuestionByIdUseCase: UpdateQuestionByIdUseCase,
    private val deleteQuestionByIdUseCase: DeleteQuestionByIdUseCase,
    private val saveQuestionToEvaluationFormUseCase: SaveQuestionToEvaluationFormUseCase
) : ViewModel() {

    private val _evaluationForm = MutableStateFlow<EvaluationForm?>(null)
    val evaluationForm = _evaluationForm.asStateFlow()

    private val _questions = MutableStateFlow<List<EvaluationQuestion>>(emptyList())
    val questions = _questions.asStateFlow()

    private val _newQuestion = MutableStateFlow("")
    val newQuestion = _newQuestion.asStateFlow()

    private val _selectedQuestion = MutableStateFlow<EvaluationQuestion?>(null)
    val selectedQuestion = _selectedQuestion.asStateFlow()

    private val _showNewQuestionDialog = MutableStateFlow(false)
    val showNewQuestionDialog = _showNewQuestionDialog.asStateFlow()

    private val _showDeleteEvaluationDialog = MutableStateFlow(false)
    val showDeleteEvaluationDialog = _showDeleteEvaluationDialog.asStateFlow()

    private val _showResultDialog = MutableStateFlow(false)
    val showResultDialog = _showResultDialog.asStateFlow()

    private val _showUpdateQuestionDialog = MutableStateFlow(false)
    val showUpdateQuestionDialog = _showUpdateQuestionDialog.asStateFlow()

    private val _showDeleteQuestionDialog = MutableStateFlow(false)
    val showDeleteQuestionDialog = _showDeleteQuestionDialog.asStateFlow()

    private val _response = MutableStateFlow<Result?>(null)
    val response = _response.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _deleteResponse = MutableStateFlow<Result?>(null)
    val deleteResponse = _deleteResponse.asStateFlow()


    init {
        viewModelScope.launch {
            _isLoading.value = true
            val evaluationForm = getEvaluationFormByIdUseCase(evaluationId)
            _evaluationForm.value = evaluationForm
            getQuestionsByEvaluationIdUseCase(evaluationId).collect {
                _questions.value = it
                _isLoading.value = false
            }
        }
    }

    private suspend fun refreshQuestions() {
        getQuestionsByEvaluationIdUseCase(evaluationId).collect {
            _questions.value = it
        }
    }

    fun refreshData() {
        viewModelScope.launch {
            val evaluationForm = getEvaluationFormByIdUseCase(evaluationId)
            _evaluationForm.value = evaluationForm
        }
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
            try {
                saveQuestionToEvaluationFormUseCase(
                    question = EvaluationQuestion(
                        questionText = question,
                        evaluationFormId = evaluationId
                    )
                )
                _response.value = Result(
                    success = true,
                    message = "Question added"
                )
            } catch (e: Exception) {
                _response.value = Result(
                    success = false,
                    message = "Error adding question"
                )
            } finally {
                setShowNewQuestionDialog()
            }
        }
    }

    fun setShowDeleteEvaluationDialog(value: Boolean) {
        _showDeleteEvaluationDialog.value = value
    }

    fun setShowResultDialog(value: Boolean) {
        _showResultDialog.value = value
    }

    fun setShowUpdateQuestionDialog(value: Boolean) {
        _showUpdateQuestionDialog.value = value
    }

    fun setShowDeleteQuestionDIalog(value: Boolean) {
        _showDeleteQuestionDialog.value = value
    }

    fun setSelectedQuestion(value: EvaluationQuestion) {
        _selectedQuestion.value = value
    }

    fun updateSelectedQuestion(questionText: String) {
        val updateQuestion = EvaluationQuestion(
            id = _selectedQuestion.value?.id ?: 0,
            questionText = questionText,
            evaluationFormId = evaluationId
        )
        viewModelScope.launch {
            try {
                updateQuestionByIdUseCase(updateQuestion)
                _showUpdateQuestionDialog.value = false
                _selectedQuestion.value = null
                refreshQuestions()
                Log.d("App", "Question $_selectedQuestion updated successfully.")
            } catch (e: Exception) {
                Log.e("App", "Error updating question: ${e.message}")
            }
        }
    }

    fun deleteSelectedQuestion() {
        viewModelScope.launch {
            try {
                Log.d("App", "Deleting selected question: ${_selectedQuestion.value}")
                deleteQuestionByIdUseCase(_selectedQuestion.value?.id ?: 0)
                _showDeleteQuestionDialog.value = false
                _selectedQuestion.value = null
            } catch (e: Exception) {
                Log.e("App", "Error deleting question: ${e.message}")
            }
        }
    }

    fun deleteEvaluation() {
        viewModelScope.launch {
            _showDeleteEvaluationDialog.value = false
            Log.d(
                "App",
                "Deleting evaluation with id: `$evaluationId`, title: `${evaluationForm.value?.title}`"
            )
            try {
                deleteEvaluationFormByIdUseCase(evaluationId)
                _deleteResponse.value = Result(
                    success = true,
                    message = "Evaluation deleted"
                )
            } catch (e: Exception) {
                Log.e("App", "Error deleting evaluation form: ${e.message}")
                _deleteResponse.value = Result(
                    success = false,
                    message = "Error deleting evaluation form"
                )
            }
            _showResultDialog.value = true
        }
    }
}