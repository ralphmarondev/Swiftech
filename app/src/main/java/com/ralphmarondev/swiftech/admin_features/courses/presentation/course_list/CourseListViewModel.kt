package com.ralphmarondev.swiftech.admin_features.courses.presentation.course_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ralphmarondev.swiftech.core.domain.model.Course
import com.ralphmarondev.swiftech.core.domain.usecases.course.GetAllCoursesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CourseListViewModel(
    private val getAllCoursesUseCase: GetAllCoursesUseCase
) : ViewModel() {

    private val _courses = MutableStateFlow<List<Course>>(emptyList())
    val courses = _courses.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    init {
        viewModelScope.launch {
            _isLoading.value = true
            getAllCoursesUseCase().collect { courseList ->
                _courses.value = courseList
                _isLoading.value = false
            }
        }
    }
}