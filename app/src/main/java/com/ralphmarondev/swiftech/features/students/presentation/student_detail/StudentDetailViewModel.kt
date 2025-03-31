package com.ralphmarondev.swiftech.features.students.presentation.student_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ralphmarondev.swiftech.core.domain.model.User
import com.ralphmarondev.swiftech.core.domain.usecases.GetUserDetailByUsername
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class StudentDetailViewModel(
    private val username: String,
    private val getUserDetailByUsername: GetUserDetailByUsername
) : ViewModel() {
    private val _userDetail = MutableStateFlow<User?>(null)
    val userDetail = _userDetail.asStateFlow()

    init {
        viewModelScope.launch {
            val user = getUserDetailByUsername(username)
            _userDetail.value = user
        }
    }
}