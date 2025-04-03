package com.ralphmarondev.swiftech.features.courses.presentation.new_course

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class NewCourseViewModel : ViewModel() {

    init {
        viewModelScope.launch {
            println("New course viewmodel is initialized...")
        }
    }
}