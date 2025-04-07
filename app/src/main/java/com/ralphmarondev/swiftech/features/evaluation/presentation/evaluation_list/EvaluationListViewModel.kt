package com.ralphmarondev.swiftech.features.evaluation.presentation.evaluation_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ralphmarondev.swiftech.core.domain.model.EvaluationForm
import com.ralphmarondev.swiftech.core.domain.usecases.evaluation.GetAllEvaluationFormsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class EvaluationListViewModel(
    private val getAllEvaluationFormsUseCase: GetAllEvaluationFormsUseCase
) : ViewModel() {

    private val _evaluations = MutableStateFlow<List<EvaluationForm>>(emptyList())
    val evaluations = _evaluations.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    init {
        viewModelScope.launch {
            _isLoading.value = true
            getAllEvaluationFormsUseCase().collect {
                _evaluations.value = it
                _isLoading.value = false
            }
        }
    }
}