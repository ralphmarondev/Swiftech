package com.ralphmarondev.swiftech.features.teachers.presentation.teacher_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ralphmarondev.swiftech.core.domain.model.Role
import com.ralphmarondev.swiftech.core.domain.model.User
import com.ralphmarondev.swiftech.core.domain.usecases.GetAllUserByRoleUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TeacherListViewModel(
    private val getAllUserByRole: GetAllUserByRoleUseCase
) : ViewModel() {

    private val _teachers = MutableStateFlow<List<User>>(emptyList())
    val teachers = _teachers.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    init {
        viewModelScope.launch {
            _isLoading.value = true
            getAllUserByRole(role = Role.TEACHER).collect { teacherList ->
                _teachers.value = teacherList
                _isLoading.value = false
            }
        }
    }
}