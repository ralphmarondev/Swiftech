package com.ralphmarondev.swiftech.student_features.evaluate.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ralphmarondev.swiftech.core.domain.usecases.course.GetCourseDetailByIdUseCase
import com.ralphmarondev.swiftech.core.domain.usecases.user.GetUserByIdUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class EvaluateViewModel(
    private val id: Int,
    private val getCourseDetailByIdUseCase: GetCourseDetailByIdUseCase,
    private val getUserByIdUseCase: GetUserByIdUseCase
) : ViewModel() {

    private val _courseName = MutableStateFlow("")
    val courseName = _courseName.asStateFlow()

    private val _courseTeacher = MutableStateFlow("")
    val courseTeacher = _courseTeacher.asStateFlow()

    private val _questions = MutableStateFlow<List<String>>(emptyList())
    val questions = _questions.asStateFlow()

    init {
        viewModelScope.launch {
            val course = getCourseDetailByIdUseCase(id)
            _courseName.value = course?.name ?: "Course name not defined."

            _courseTeacher.value = getUserByIdUseCase(course?.teacherId ?: -1)?.fullName
                ?: "Teacher name not defined."

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