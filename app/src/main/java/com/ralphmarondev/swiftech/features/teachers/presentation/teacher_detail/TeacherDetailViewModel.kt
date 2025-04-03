package com.ralphmarondev.swiftech.features.teachers.presentation.teacher_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ralphmarondev.swiftech.core.data.local.preferences.AppPreferences
import com.ralphmarondev.swiftech.core.domain.model.User
import com.ralphmarondev.swiftech.core.domain.usecases.DeleteUserUseCase
import com.ralphmarondev.swiftech.core.domain.usecases.GetUserDetailByUsername
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TeacherDetailViewModel(
    private val username: String,
    private val getUserDetailByUsername: GetUserDetailByUsername,
    private val deleteUserUseCase: DeleteUserUseCase,
    private val preferences: AppPreferences
) : ViewModel() {

    private val _userDetail = MutableStateFlow<User?>(null)
    val userDetail = _userDetail.asStateFlow()

    private val _showDeleteDialog = MutableStateFlow(false)
    val showDeleteDialog = _showDeleteDialog.asStateFlow()

    init {
        preferences.setToUpdateUsername(username)
        fetchDetails()
    }

    private fun fetchDetails() {
        viewModelScope.launch {
            val user = getUserDetailByUsername(
                preferences.getToUpdateUsername() ?: "No username provided."
            )
            _userDetail.value = user
        }
    }

    fun refreshDetails() {
        fetchDetails()
    }

    fun setDeleteDialog() {
        _showDeleteDialog.value = !_showDeleteDialog.value
    }

    fun deleteUser() {
        viewModelScope.launch {
            deleteUserUseCase(userDetail.value?.id ?: -1)
        }
    }
}