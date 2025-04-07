package com.ralphmarondev.swiftech.features.evaluation.presentation.evaluation_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ralphmarondev.swiftech.core.domain.model.EvaluationForm
import com.ralphmarondev.swiftech.core.domain.model.Result
import com.ralphmarondev.swiftech.core.domain.usecases.evaluation.GetEvaluationFormByIdUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class EvaluationDetailViewModel(
    private val evaluationId: Int,
    private val getEvaluationFormByIdUseCase: GetEvaluationFormByIdUseCase
) : ViewModel() {

    private val _evaluationForm = MutableStateFlow<EvaluationForm?>(null)
    val evaluationForm = _evaluationForm.asStateFlow()

    private val _newQuestion = MutableStateFlow("")
    val newQuestion = _newQuestion.asStateFlow()

    private val _showNewQuestionDialog = MutableStateFlow(false)
    val showNewQuestionDialog = _showNewQuestionDialog.asStateFlow()

    private val _response = MutableStateFlow<Result?>(null)
    val response = _response.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()


    init {
        viewModelScope.launch {
            _isLoading.value = true
            val evaluationForm = getEvaluationFormByIdUseCase(evaluationId)
            _evaluationForm.value = evaluationForm
            _isLoading.value = false
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
}