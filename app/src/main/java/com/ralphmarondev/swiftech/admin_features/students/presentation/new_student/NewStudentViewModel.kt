package com.ralphmarondev.swiftech.admin_features.students.presentation.new_student

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ralphmarondev.swiftech.core.data.local.preferences.AppPreferences
import com.ralphmarondev.swiftech.core.domain.model.Gender
import com.ralphmarondev.swiftech.core.domain.model.Result
import com.ralphmarondev.swiftech.core.domain.model.Role
import com.ralphmarondev.swiftech.core.domain.model.User
import com.ralphmarondev.swiftech.core.domain.usecases.user.CreateUserUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NewStudentViewModel(
    private val createUserUseCase: CreateUserUseCase,
    private val preferences: AppPreferences
) : ViewModel() {

    private val defaultImage = preferences.getDefaultImage()

    private val _fullName = MutableStateFlow("")
    val fullName = _fullName.asStateFlow()

    private val _username = MutableStateFlow("")
    val username = _username.asStateFlow()

    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()

    private val _gender = MutableStateFlow(Gender.MALE)
    val gender = _gender.asStateFlow()

    private val _imagePath = MutableStateFlow(defaultImage ?: "")
    val imagePath = _imagePath.asStateFlow()

    private val _response = MutableStateFlow<Result?>(null)
    val response = _response.asStateFlow()


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

    fun register() {
        viewModelScope.launch {
            val fullName = _fullName.value.trim()
            val username = _username.value.trim()
            val password = _password.value.trim()
            val gender = _gender.value.trim()

            if (fullName.isEmpty() && username.isEmpty() && password.isEmpty()) {
                _response.value = Result(
                    success = false,
                    message = "Please fill in all fields."
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

            createUserUseCase(
                user = User(
                    username = username,
                    password = password,
                    fullName = fullName,
                    role = Role.STUDENT,
                    gender = gender
                )
            )
            _response.value = Result(
                success = true,
                message = "Registration successful."
            )
            Log.d(
                "App",
                "Full name: $fullName, username: $username, password: $password, gender: $gender"
            )
            _fullName.value = ""
            _username.value = ""
            _password.value = ""
            _gender.value = Gender.MALE
        }
    }
}