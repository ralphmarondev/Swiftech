package com.ralphmarondev.swiftech.teacher_features.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ralphmarondev.swiftech.core.data.local.preferences.AppPreferences
import com.ralphmarondev.swiftech.core.domain.model.Course
import com.ralphmarondev.swiftech.core.domain.model.User
import com.ralphmarondev.swiftech.core.domain.usecases.user.GetUserDetailByUsername
import com.ralphmarondev.swiftech.teacher_features.home.domain.usecase.GetCoursesByTeacherIdUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val username: String,
    private val getUserDetailByUsername: GetUserDetailByUsername,
    private val getCoursesByTeacherIdUseCase: GetCoursesByTeacherIdUseCase,
    private val preferences: AppPreferences
) : ViewModel() {

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser = _currentUser.asStateFlow()

    private val _showConfirmExitDialog = MutableStateFlow(false)
    val showConfirmExitDialog = _showConfirmExitDialog.asStateFlow()

    private val _courses = MutableStateFlow<List<Course>>(emptyList())
    val courses = _courses.asStateFlow()

    init {
        viewModelScope.launch {
            _currentUser.value = getUserDetailByUsername(username)
            preferences.setStudentId(_currentUser.value?.id ?: 0)
            getCoursesByTeacherIdUseCase(_currentUser.value?.id ?: 0).collect {
                _courses.value = it
            }
        }
    }

    fun setShowConfirmExitDialog() {
        _showConfirmExitDialog.value = !_showConfirmExitDialog.value
    }
}