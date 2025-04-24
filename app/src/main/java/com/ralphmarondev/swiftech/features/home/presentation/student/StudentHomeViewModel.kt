package com.ralphmarondev.swiftech.features.home.presentation.student

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ralphmarondev.swiftech.core.domain.model.User
import com.ralphmarondev.swiftech.core.domain.usecases.user.GetUserDetailByUsername
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class StudentHomeViewModel(
    private val username: String,
    private val getUserDetailByUsername: GetUserDetailByUsername
) : ViewModel() {

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser = _currentUser.asStateFlow()

    private val _showConfirmExitDialog = MutableStateFlow(false)
    val showConfirmExitDialog = _showConfirmExitDialog.asStateFlow()

    init {
        viewModelScope.launch {
            _currentUser.value = getUserDetailByUsername(username)
        }
    }

    fun setShowConfirmExitDialog() {
        _showConfirmExitDialog.value = !_showConfirmExitDialog.value
    }
}