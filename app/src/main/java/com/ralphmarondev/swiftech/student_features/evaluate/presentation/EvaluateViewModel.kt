package com.ralphmarondev.swiftech.student_features.evaluate.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ralphmarondev.swiftech.core.data.local.preferences.AppPreferences
import com.ralphmarondev.swiftech.core.domain.usecases.course.GetCourseDetailByIdUseCase
import com.ralphmarondev.swiftech.core.domain.usecases.user.GetUserByIdUseCase
import com.ralphmarondev.swiftech.student_features.evaluate.domain.model.QuestionRating
import com.ralphmarondev.swiftech.student_features.evaluate.domain.usecase.GetEvaluationFormQuestionByIdUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class EvaluateViewModel(
    private val formId: Int,
    private val preferences: AppPreferences,
    private val getCourseDetailByIdUseCase: GetCourseDetailByIdUseCase,
    private val getUserByIdUseCase: GetUserByIdUseCase,
    private val getEvaluationFormQuestionByIdUseCase: GetEvaluationFormQuestionByIdUseCase
) : ViewModel() {

    private val _courseName = MutableStateFlow("")
    val courseName = _courseName.asStateFlow()

    private val _courseTeacher = MutableStateFlow("")
    val courseTeacher = _courseTeacher.asStateFlow()

    private val _questions = MutableStateFlow<List<QuestionRating>>(emptyList())
    val questions = _questions.asStateFlow()

    init {
        viewModelScope.launch {
            // NOTE: THIS IS SET ON `EVALUATION_FORMS`
            val courseId = preferences.getCourseId()

            _courseName.value =
                getCourseDetailByIdUseCase(
                    courseId
                )?.name ?: "Course name is not specified."
            _courseTeacher.value = getUserByIdUseCase(
                id = getCourseDetailByIdUseCase(
                    courseId
                )?.teacherId ?: 0
            )?.fullName ?: "Teacher name is not specified."

            getEvaluationFormQuestionByIdUseCase(formId).collect { questions ->
                _questions.value = questions.map { QuestionRating(it.questionText) }
            }
        }
    }

    fun selectRating(question: String, rating: String) {
        _questions.value = _questions.value.map {
            if (it.question == question) {
                it.copy(selectedRating = rating)
            } else it
        }
    }
}