package com.ralphmarondev.swiftech.features.auth.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ralphmarondev.swiftech.core.data.local.preferences.AppPreferences
import com.ralphmarondev.swiftech.features.auth.domain.model.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val preferences: AppPreferences
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
        if (preferences.isRememberMeChecked()) {
            val savedUsername = preferences.getRememberedUsername()
            val savedPassword = preferences.getRememberedPassword()

            _username.value = savedUsername ?: ""
            _password.value = savedPassword ?: ""
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

            if (username.isEmpty() || password.isEmpty()) {
                _response.value = Result(
                    success = false,
                    message = "Username or password cannot be empty!"
                )
                return@launch
            }

            if (username == "jami" && password == "jami") {
                _response.value = Result(
                    success = true,
                    message = "Login successful."
                )
                if (_rememberMe.value) {
                    preferences.setUsernameToRemember(_username.value.trim())
                    preferences.setPasswordToRemember(_password.value.trim())
                }
            } else {
                _response.value = Result(
                    success = false,
                    message = "Invalid username or password!"
                )
            }
        }
    }
}