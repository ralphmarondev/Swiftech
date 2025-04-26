package com.ralphmarondev.swiftech.student_features.evaluation_forms.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ralphmarondev.swiftech.core.data.local.preferences.AppPreferences
import com.ralphmarondev.swiftech.core.domain.model.EvaluationForm
import com.ralphmarondev.swiftech.core.domain.usecases.course.GetCourseDetailByIdUseCase
import com.ralphmarondev.swiftech.student_features.evaluation_forms.domain.usecase.GetEvaluationFormByTermUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class EvaluationFormViewModel(
    private val courseId: Int,
    private val preferences: AppPreferences,
    private val getCourseDetailByIdUseCase: GetCourseDetailByIdUseCase,
    private val getEvaluationFormByTermUseCase: GetEvaluationFormByTermUseCase
) : ViewModel() {

    private val _evaluationForms = MutableStateFlow<List<EvaluationForm>>(emptyList())
    val evaluationForms = _evaluationForms.asStateFlow()

    init {
        viewModelScope.launch {
            // NOTE: THIS IS USED ON `EVALUATE`
            preferences.setCourseId(courseId)
            val course = getCourseDetailByIdUseCase(courseId)
            getEvaluationFormByTermUseCase(
                term = course?.term ?: "Term is not specified."
            ).collect {
                _evaluationForms.value = it
            }
        }
    }
}