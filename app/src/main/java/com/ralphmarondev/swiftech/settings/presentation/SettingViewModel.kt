package com.ralphmarondev.swiftech.settings.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ralphmarondev.swiftech.core.data.local.preferences.AppPreferences
import com.ralphmarondev.swiftech.core.domain.model.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SettingViewModel(
    private val preferences: AppPreferences
) : ViewModel() {

    private val _showDeleteSavedCredentialsDialog = MutableStateFlow(false)
    val showDeleteSavedCredentialsDialog = _showDeleteSavedCredentialsDialog.asStateFlow()

    private val _showResultDialog = MutableStateFlow(false)
    val showResultDialog = _showResultDialog.asStateFlow()

    private val _response = MutableStateFlow<Result?>(null)
    val response = _response.asStateFlow()

    fun setShowDeleteSavedCredentialDialog(value: Boolean) {
        _showDeleteSavedCredentialsDialog.value = value
    }

    fun setShowResultDialog(value: Boolean) {
        _showResultDialog.value = value
    }

    fun deleteSavedCredentials() {
        viewModelScope.launch {
            try {
                preferences.setUsernameToRemember("")
                preferences.setPasswordToRemember("")
                setShowDeleteSavedCredentialDialog(false)
                setShowResultDialog(true)
                _response.value = Result(
                    success = true,
                    message = "Saved credentials deleted successfully."
                )
            } catch (e: Exception) {
                _response.value = Result(
                    success = false,
                    message = "Deleting saved credentials failed."
                )
            }
        }
    }
}