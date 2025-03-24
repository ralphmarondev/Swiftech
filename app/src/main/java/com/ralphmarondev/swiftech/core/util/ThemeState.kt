package com.ralphmarondev.swiftech.core.util

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.ralphmarondev.swiftech.core.data.local.preferences.AppPreferences

class ThemeState(
    private val preferences: AppPreferences
) {
    private val _darkTheme = mutableStateOf(preferences.isDarkTheme())
    val darkTheme: State<Boolean> get() = _darkTheme

    fun toggleTheme() {
        _darkTheme.value = !darkTheme.value
        preferences.setDarkTheme()
    }
}