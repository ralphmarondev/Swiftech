package com.ralphmarondev.swiftech.features.home.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ralphmarondev.swiftech.R
import com.ralphmarondev.swiftech.core.data.local.preferences.AppPreferences
import com.ralphmarondev.swiftech.core.domain.model.User
import com.ralphmarondev.swiftech.core.util.saveDrawableToInternalStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val context: Context,
    private val preferences: AppPreferences
) : ViewModel() {
    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser = _currentUser.asStateFlow()

    init {
        viewModelScope.launch {
            val imagePath = saveDrawableToInternalStorage(
                context = context,
                drawableRes = R.drawable.profile,
                fileName = "profile.jpg"
            )
            val currentUser = preferences.getCurrentUser() ?: ""
            if (currentUser == "") {
                _currentUser.value = User(
                    username = "jami",
                    password = "jami",
                    role = "Administrator",
                    fullName = "Jamille Rivera",
                    image = imagePath
                )
            }

            if (currentUser == "jam") {
                _currentUser.value = User(
                    username = "jam",
                    password = "jam",
                    role = "Administrator",
                    fullName = "Jamille Rivera",
                    image = imagePath
                )
            } else if (currentUser == "jami") {
                _currentUser.value = User(
                    username = "jami",
                    password = "jami",
                    role = "Teacher",
                    fullName = "Jamille Rivera",
                    image = imagePath
                )
            } else {
                _currentUser.value = User(
                    username = "jamille",
                    password = "jamille",
                    role = "Student",
                    fullName = "Jamille Rivera",
                    image = imagePath
                )
            }
        }
    }
}