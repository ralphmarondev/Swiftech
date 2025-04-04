package com.ralphmarondev.swiftech.features.courses.presentation.new_course

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ralphmarondev.swiftech.core.data.local.preferences.AppPreferences
import com.ralphmarondev.swiftech.core.domain.model.Course
import com.ralphmarondev.swiftech.core.domain.model.Result
import com.ralphmarondev.swiftech.core.domain.usecases.course.CreateCourseUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NewCourseViewModel(
    private val preferences: AppPreferences,
    private val createCourseUseCase: CreateCourseUseCase
) : ViewModel() {

    private val defaultImage = preferences.getDefaultImage()

    private val _name = MutableStateFlow("")
    val name = _name.asStateFlow()

    private val _code = MutableStateFlow("")
    val code = _code.asStateFlow()

    private val teacherId = MutableStateFlow(-1)
    val teacher = teacherId.asStateFlow()

    private val _imagePath = MutableStateFlow(defaultImage ?: "")
    val imagePath = _imagePath.asStateFlow()

    private val _response = MutableStateFlow<Result?>(null)
    val response = _response.asStateFlow()


    fun onFullNameChange(value: String) {
        _name.value = value
    }

    fun onUsernameChange(value: String) {
        _code.value = value
    }

    fun onPasswordChange(value: Int) {
        teacherId.value = value
    }

    fun onImagePathChange(value: String) {
        _imagePath.value = value
    }

    init {
        viewModelScope.launch {
            println("New course viewmodel is initialized...")
        }
    }

    fun register() {
        viewModelScope.launch {
            createCourseUseCase(
                course = Course(
                    name = name.value,
                    code = code.value,
                    teacherId = teacher.value,
                    image = imagePath.value
                )
            )
        }
    }
}