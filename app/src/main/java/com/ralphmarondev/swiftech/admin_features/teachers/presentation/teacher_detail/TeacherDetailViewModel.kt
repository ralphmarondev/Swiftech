package com.ralphmarondev.swiftech.admin_features.teachers.presentation.teacher_detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ralphmarondev.swiftech.admin_features.teachers.domain.usecase.IsTeacherAssignedToAnyClassUseCase
import com.ralphmarondev.swiftech.core.data.local.preferences.AppPreferences
import com.ralphmarondev.swiftech.core.domain.model.Result
import com.ralphmarondev.swiftech.core.domain.model.User
import com.ralphmarondev.swiftech.core.domain.usecases.user.DeleteUserUseCase
import com.ralphmarondev.swiftech.core.domain.usecases.user.GetUserDetailByUsername
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TeacherDetailViewModel(
    private val username: String,
    private val getUserDetailByUsername: GetUserDetailByUsername,
    private val deleteUserUseCase: DeleteUserUseCase,
    private val preferences: AppPreferences,
    private val isTeacherAssignedToAnyClassUseCase: IsTeacherAssignedToAnyClassUseCase
) : ViewModel() {

    private val _userDetail = MutableStateFlow<User?>(null)
    val userDetail = _userDetail.asStateFlow()

    private val _showDeleteDialog = MutableStateFlow(false)
    val showDeleteDialog = _showDeleteDialog.asStateFlow()

    private val _showResultDialog = MutableStateFlow(false)
    val showResultDialog = _showResultDialog.asStateFlow()

    private val _response = MutableStateFlow<Result?>(null)
    val response = _response.asStateFlow()


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
            try {
                _showDeleteDialog.value = false
                val hasClasses = isTeacherAssignedToAnyClassUseCase(userDetail.value?.id ?: -1)

                if (hasClasses) {
                    _response.value = Result(
                        success = false,
                        message = "Cannot delete this teacher. They are currently assigned to classes. Please reassign or remove them from classes first."
                    )
                    _showResultDialog.value = true
                    return@launch
                }

                deleteUserUseCase(userDetail.value?.id ?: -1)
                _response.value = Result(
                    success = true,
                    message = "Teacher deleted successfully."
                )
                _showResultDialog.value = true
            } catch (e: Exception) {
                Log.e("App", "Error deleting teacher: ${e.message}")
            }
        }
    }

    fun setShowResultDialog(value: Boolean) {
        _showResultDialog.value = value
    }
}