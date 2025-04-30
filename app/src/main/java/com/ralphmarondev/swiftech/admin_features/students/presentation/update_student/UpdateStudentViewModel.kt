package com.ralphmarondev.swiftech.admin_features.students.presentation.update_student

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ralphmarondev.swiftech.core.data.local.preferences.AppPreferences
import com.ralphmarondev.swiftech.core.domain.model.Gender
import com.ralphmarondev.swiftech.core.domain.model.Result
import com.ralphmarondev.swiftech.core.domain.model.User
import com.ralphmarondev.swiftech.core.domain.usecases.user.GetUserDetailByUsername
import com.ralphmarondev.swiftech.core.domain.usecases.user.UpdateUserUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UpdateStudentViewModel(
    private val usernameArgs: String,
    private val getUserDetailByUsername: GetUserDetailByUsername,
    private val updateUserUseCase: UpdateUserUseCase,
    private val preferences: AppPreferences
) : ViewModel() {

    private val _selectedStudent = MutableStateFlow<User?>(null)

    private val _fullName = MutableStateFlow("")
    val fullName = _fullName.asStateFlow()

    private val _username = MutableStateFlow("")
    val username = _username.asStateFlow()

    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()

    private val _gender = MutableStateFlow("")
    val gender = _gender.asStateFlow()

    private val _imagePath = MutableStateFlow<String?>(null)
    val imagePath = _imagePath.asStateFlow()

    private val _response = MutableStateFlow<Result?>(null)
    val response = _response.asStateFlow()

    private val _updateStudentDialog = MutableStateFlow(false)
    val updateStudentDialog = _updateStudentDialog.asStateFlow()

    init {
        viewModelScope.launch {
            val user = getUserDetailByUsername(usernameArgs)
            _selectedStudent.value = user
            _fullName.value = user?.fullName ?: "No full name provided"
            _username.value = user?.username ?: "No username provided"
            _password.value = user?.password ?: "No password provided"
            _imagePath.value = user?.image
            _gender.value = user?.gender ?: "No gender provided"
        }
    }

    fun onFullNameChange(value: String) {
        _fullName.value = value
    }

    fun onUsernameChange(value: String) {
        _username.value = value
    }

    fun onPasswordChange(value: String) {
        _password.value = value
    }

    fun onGenderValueChange(value: String) {
        _gender.value = value
    }

    fun onImagePathChange(value: String) {
        _imagePath.value = value
    }

    fun updateStudent() {
        viewModelScope.launch {
            val fullName = _fullName.value.trim()
            val username = _username.value.trim()
            val password = _password.value.trim()
            val gender = _gender.value.trim()

            if (fullName.isEmpty() && username.isEmpty() && password.isEmpty()) {
                _response.value = Result(
                    success = false,
                    message = "All fields are required!"
                )
                return@launch
            }
            if (fullName.isEmpty()) {
                _response.value = Result(
                    success = false,
                    message = "Full name cannot be empty!"
                )
                return@launch
            }
            if (username.isEmpty()) {
                _response.value = Result(
                    success = false,
                    message = "Username cannot be empty!"
                )
                return@launch
            }
            if (password.isEmpty()) {
                _response.value = Result(
                    success = false,
                    message = "Password cannot be empty!"
                )
                return@launch
            }
            if (gender.isEmpty()) {
                _response.value = Result(
                    success = false,
                    message = "Gender cannot be empty!"
                )
                return@launch
            }

            try {
                updateUserUseCase(
                    user = User(
                        id = _selectedStudent.value?.id ?: -1,
                        fullName = fullName,
                        username = username,
                        password = password,
                        gender = gender,
                        image = _imagePath.value,
                        role = _selectedStudent.value?.role ?: "role not found."
                    )
                )
                _response.value = Result(
                    success = true,
                    message = "Student details updated successfully."
                )
                preferences.setToUpdateUsername(username)
                Log.d("App", "Full name: $fullName, username: $username, password: $password")
                _fullName.value = ""
                _username.value = ""
                _password.value = ""
                _gender.value = Gender.MALE
                _updateStudentDialog.value = true
            } catch (e: Exception) {
                Log.e("App", "Error updating student. Error: ${e.message}")
                _response.value = Result(
                    success = false,
                    message = "Updating student failed."
                )
            }
        }
    }

    fun setUpdateStudentDialog(value: Boolean) {
        _updateStudentDialog.value = value
    }
}