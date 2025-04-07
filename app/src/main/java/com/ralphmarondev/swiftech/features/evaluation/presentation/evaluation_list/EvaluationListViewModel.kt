package com.ralphmarondev.swiftech.features.evaluation.presentation.evaluation_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ralphmarondev.swiftech.core.domain.model.EvaluationForm
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class EvaluationListViewModel : ViewModel() {

    private val _evaluations = MutableStateFlow<List<EvaluationForm>>(emptyList())
    val evaluations = _evaluations.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    init {
        viewModelScope.launch {
            _isLoading.value = true
            _evaluations.value += EvaluationForm(
                title = "Test evaluation",
                description = "This is a test evaluation"
            )
            delay(2000)
            _isLoading.value = false
        }
    }
}