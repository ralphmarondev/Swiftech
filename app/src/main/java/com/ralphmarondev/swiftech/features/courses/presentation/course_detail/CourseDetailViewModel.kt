package com.ralphmarondev.swiftech.features.courses.presentation.course_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ralphmarondev.swiftech.core.domain.model.Course
import com.ralphmarondev.swiftech.core.domain.model.User
import com.ralphmarondev.swiftech.core.domain.usecases.course.GetCourseDetailByIdUseCase
import com.ralphmarondev.swiftech.core.domain.usecases.user.GetUserByIdUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CourseDetailViewModel(
    private val courseId: Int,
    private val getCourseDetailByIdUseCase: GetCourseDetailByIdUseCase,
    private val getUserByIdUseCase: GetUserByIdUseCase
) : ViewModel() {

    private val _courseDetail = MutableStateFlow<Course?>(null)
    val courseDetail = _courseDetail.asStateFlow()

    private val _teacherDetail = MutableStateFlow<User?>(null)
    val teacherDetail = _teacherDetail.asStateFlow()

    init {
        viewModelScope.launch {
            val course = getCourseDetailByIdUseCase(courseId)
            _courseDetail.value = course
            val teacher = getUserByIdUseCase(course?.teacherId ?: 0)
            _teacherDetail.value = teacher
        }
    }
}