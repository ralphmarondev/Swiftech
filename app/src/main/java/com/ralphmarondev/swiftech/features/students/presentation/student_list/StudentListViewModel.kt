package com.ralphmarondev.swiftech.features.students.presentation.student_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ralphmarondev.swiftech.core.domain.model.Role
import com.ralphmarondev.swiftech.core.domain.model.User
import com.ralphmarondev.swiftech.core.domain.usecases.GetAllUserByRoleUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class StudentListViewModel(
    private val getAllUserByRole: GetAllUserByRoleUseCase
) : ViewModel() {

    private val _students = MutableStateFlow<List<User>>(emptyList())
    val students = _students.asStateFlow()

    init {
        viewModelScope.launch {
            getAllUserByRole(role = Role.STUDENT).collect { studentList ->
                _students.value = studentList
            }
        }
    }
}