package com.ralphmarondev.swiftech.core.data.local.preferences

import android.content.Context
import androidx.core.content.edit

class AppPreferences(
    private val context: Context
) {
    companion object {
        private const val PREFERENCE_NAME = "app_preferences"
        private const val FIRST_LAUNCH = "first_launch"
        private const val DARK_THEME = "dark_theme"
        private const val REMEMBER_ME = "remember_me"
        private const val SAVED_USER_USERNAME = "saved_user_username"
        private const val SAVED_USER_PASSWORD = "saved_user_password"
        private const val CURRENT_USER = "current_user"

        private const val DEFAULT_IMAGE = "default_image"
        private const val TO_UPDATE_USERNAME = "to_update_username"

        private const val ROLE = "role"
    }

    private val sharedPreferences = context.getSharedPreferences(
        PREFERENCE_NAME,
        Context.MODE_PRIVATE
    )

    fun isFirstLaunch(): Boolean {
        return sharedPreferences.getBoolean(FIRST_LAUNCH, true)
    }

    fun setIsFirstLaunchDone() {
        sharedPreferences.edit { putBoolean(FIRST_LAUNCH, false) }
    }

    fun isDarkTheme(): Boolean {
        return sharedPreferences.getBoolean(DARK_THEME, false)
    }

    fun setDarkTheme(value: Boolean = !isDarkTheme()) {
        sharedPreferences.edit { putBoolean(DARK_THEME, value) }
    }

    fun setRememberMe() {
        sharedPreferences.edit { putBoolean(REMEMBER_ME, !isRememberMeChecked()) }
    }

    fun isRememberMeChecked(): Boolean {
        return sharedPreferences.getBoolean(REMEMBER_ME, true)
    }

    fun setUsernameToRemember(value: String) {
        sharedPreferences.edit { putString(SAVED_USER_USERNAME, value) }
    }

    fun getRememberedUsername(): String? {
        return sharedPreferences.getString(SAVED_USER_USERNAME, null)
    }

    fun setPasswordToRemember(value: String) {
        sharedPreferences.edit { putString(SAVED_USER_PASSWORD, value) }
    }

    fun getRememberedPassword(): String? {
        return sharedPreferences.getString(SAVED_USER_PASSWORD, null)
    }

    fun setCurrentUser(value: String) {
        sharedPreferences.edit { putString(CURRENT_USER, value) }
    }

    fun getCurrentUser(): String? {
        return sharedPreferences.getString(CURRENT_USER, null)
    }

    fun setDefaultImage(value: String) {
        sharedPreferences.edit { putString(DEFAULT_IMAGE, value) }
    }

    fun getDefaultImage(): String? {
        return sharedPreferences.getString(DEFAULT_IMAGE, null)
    }


    // Temporary solution
    fun setToUpdateUsername(value: String) {
        sharedPreferences.edit { putString(TO_UPDATE_USERNAME, value) }
    }

    fun getToUpdateUsername(): String? {
        return sharedPreferences.getString(TO_UPDATE_USERNAME, null)
    }


    fun setRole(value: String) {
        sharedPreferences.edit { putString(ROLE, value) }
    }

    fun getRole(): String? {
        return sharedPreferences.getString(ROLE, null)
    }
}