package com.ralphmarondev.swiftech.features.evaluation.presentation.new_evaluation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ralphmarondev.swiftech.core.domain.model.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NewEvaluationViewModel : ViewModel() {

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
}