package com.ralphmarondev.swiftech.student_features.evaluate.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class EvaluateViewModel : ViewModel() {
    private val _questions = MutableStateFlow<List<String>>(emptyList())
    val questions = _questions.asStateFlow()

    init {
        viewModelScope.launch {
            _questions.value = listOf(
                "Displayed energy, enthusiasm, and commitment to teaching.",
                "Arrived promptly and remained for the agreed upon time.",
                "Demonstrated ability to learn about students.",
                "Demonstrated ability to design lessons based on student's needs.",
                "Displayed sensitivity to special needs students."
            )
        }
    }
}