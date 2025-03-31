package com.ralphmarondev.swiftech.features.auth.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ralphmarondev.swiftech.core.data.local.preferences.AppPreferences
import com.ralphmarondev.swiftech.core.domain.model.User
import com.ralphmarondev.swiftech.core.domain.usecases.CreateUserUseCase
import com.ralphmarondev.swiftech.core.domain.usecases.IsUserExistsUseCase
import com.ralphmarondev.swiftech.core.domain.model.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val preferences: AppPreferences,
    private val createUserUseCase: CreateUserUseCase,
    private val isUserExistsUseCase: IsUserExistsUseCase
) : ViewModel() {

    private val _username = MutableStateFlow("")
    val username: StateFlow<String> get() = _username

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> get() = _password

    private val _rememberMe = MutableStateFlow(preferences.isRememberMeChecked())
    val rememberMe: StateFlow<Boolean> get() = _rememberMe

    private val _showForgotPasswordDialog = MutableStateFlow(false)
    val showForgotPasswordDialog: StateFlow<Boolean> get() = _showForgotPasswordDialog

    private val _response = MutableStateFlow<Result?>(null)
    val response: StateFlow<Result?> get() = _response

    init {
        viewModelScope.launch {
            // creating default user on first launch
            if (preferences.isFirstLaunch()) {
                createUserUseCase(
                    user = User(
                        username = "jam",
                        password = "jam",
                        fullName = "",
                        role = "Admininistrator"
                    )
                )
                createUserUseCase(
                    user = User(
                        username = "jami",
                        password = "jami",
                        fullName = "",
                        role = "Teacher"
                    )
                )
                createUserUseCase(
                    user = User(
                        username = "jamille",
                        password = "jamille",
                        fullName = "",
                        role = "Student"
                    )
                )
                preferences.setIsFirstLaunchDone()
            }
            if (preferences.isRememberMeChecked()) {
                val savedUsername = preferences.getRememberedUsername()
                val savedPassword = preferences.getRememberedPassword()

                _username.value = savedUsername ?: ""
                _password.value = savedPassword ?: ""
            }
        }
    }


    fun onUsernameChange(value: String) {
        _username.value = value
    }

    fun onPasswordChange(value: String) {
        _password.value = value
    }

    fun toggleRememberMe() {
        _rememberMe.value = !_rememberMe.value
        preferences.setRememberMe()
    }

    fun toggleForgotPasswordDialog() {
        _showForgotPasswordDialog.value = !_showForgotPasswordDialog.value
    }

    fun login() {
        viewModelScope.launch {
            val username = _username.value.trim()
            val password = _password.value.trim()

            if (username.isEmpty() && password.isEmpty()) {
                _response.value = Result(
                    success = false,
                    message = "Username and password cannot be empty!"
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

            val isUserExists = isUserExistsUseCase(
                username = username,
                password = password
            )
            if (isUserExists) {
                _response.value = Result(
                    success = true,
                    message = "Login successful."
                )
                if (_rememberMe.value) {
                    preferences.setUsernameToRemember(username)
                    preferences.setPasswordToRemember(password)
                }
                preferences.setCurrentUser(username)
            } else {
                _response.value = Result(
                    success = false,
                    message = "Invalid credentials!"
                )
            }
        }
    }
}