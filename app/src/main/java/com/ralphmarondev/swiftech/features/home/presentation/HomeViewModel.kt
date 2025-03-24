package com.ralphmarondev.swiftech.features.home.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ralphmarondev.swiftech.R
import com.ralphmarondev.swiftech.core.domain.model.User
import com.ralphmarondev.swiftech.core.util.saveDrawableToInternalStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val context: Context
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
            _currentUser.value = User(
                username = "jami",
                password = "jami",
                role = "Administrator",
                fullName = "Jamille Rivera",
                image = imagePath
            )
        }
    }
}