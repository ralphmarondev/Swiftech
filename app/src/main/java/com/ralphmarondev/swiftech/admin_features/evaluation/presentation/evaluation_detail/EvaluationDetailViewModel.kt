package com.ralphmarondev.swiftech.admin_features.evaluation.presentation.evaluation_detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ralphmarondev.swiftech.core.domain.model.EvaluationForm
import com.ralphmarondev.swiftech.core.domain.model.EvaluationQuestion
import com.ralphmarondev.swiftech.core.domain.model.Result
import com.ralphmarondev.swiftech.core.domain.usecases.evaluation.DeleteEvaluationFormByIdUseCase
import com.ralphmarondev.swiftech.core.domain.usecases.evaluation.GetEvaluationFormByIdUseCase
import com.ralphmarondev.swiftech.core.domain.usecases.evaluation.GetQuestionsByEvaluationIdUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class EvaluationDetailViewModel(
    private val evaluationId: Int,
    private val getEvaluationFormByIdUseCase: GetEvaluationFormByIdUseCase,
    private val getQuestionsByEvaluationIdUseCase: GetQuestionsByEvaluationIdUseCase,
    private val deleteEvaluationFormByIdUseCase: DeleteEvaluationFormByIdUseCase
) : ViewModel() {

    private val _evaluationForm = MutableStateFlow<EvaluationForm?>(null)
    val evaluationForm = _evaluationForm.asStateFlow()

    private val _questions = MutableStateFlow<List<EvaluationQuestion>>(emptyList())
    val questions = _questions.asStateFlow()

    private val _newQuestion = MutableStateFlow("")
    val newQuestion = _newQuestion.asStateFlow()

    private val _showNewQuestionDialog = MutableStateFlow(false)
    val showNewQuestionDialog = _showNewQuestionDialog.asStateFlow()

    private val _showDeleteEvaluationDialog = MutableStateFlow(false)
    val showDeleteEvaluationDialog = _showDeleteEvaluationDialog.asStateFlow()

    private val _showResultDialog = MutableStateFlow(false)
    val showResultDialog = _showResultDialog.asStateFlow()

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

    fun refreshData(){
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
            _response.value = Result(
                success = true,
                message = "Question added"
            )
            setShowNewQuestionDialog()
        }
    }

    fun setShowDeleteEvaluationDialog(value: Boolean) {
        _showDeleteEvaluationDialog.value = value
    }

    fun setShowResultDialog(value: Boolean) {
        _showResultDialog.value = value
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