package com.ralphmarondev.swiftech.admin_features.courses.presentation.new_course

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ralphmarondev.swiftech.core.data.local.preferences.AppPreferences
import com.ralphmarondev.swiftech.core.domain.model.Course
import com.ralphmarondev.swiftech.core.domain.model.Result
import com.ralphmarondev.swiftech.core.domain.model.Role
import com.ralphmarondev.swiftech.core.domain.usecases.course.CreateCourseUseCase
import com.ralphmarondev.swiftech.core.domain.usecases.user.GetUserDetailByUsername
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NewCourseViewModel(
    private val preferences: AppPreferences,
    private val createCourseUseCase: CreateCourseUseCase,
    private val getUserDetailByUsername: GetUserDetailByUsername
) : ViewModel() {

    private val defaultImage = preferences.getDefaultImage()

    private val _name = MutableStateFlow("")
    val name = _name.asStateFlow()

    private val _code = MutableStateFlow("")
    val code = _code.asStateFlow()

    private val _term = MutableStateFlow("")
    val term = _term.asStateFlow()

    private val _teacher = MutableStateFlow("")
    val teacher = _teacher.asStateFlow()

    private val _imagePath = MutableStateFlow(defaultImage ?: "")
    val imagePath = _imagePath.asStateFlow()

    private val _response = MutableStateFlow<Result?>(null)
    val response = _response.asStateFlow()


    fun onNameValueChange(value: String) {
        _name.value = value
    }

    fun onCodeValueChange(value: String) {
        _code.value = value
    }

    fun onTeacherValueChange(value: String) {
        _teacher.value = value
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
            val name = _name.value.trim()
            val code = _code.value.trim()
            val term = _term.value.trim()
            val teacher = _teacher.value.trim()
            val imagePath = _imagePath.value.trim()

            if (name.isEmpty() && code.isEmpty() && teacher.isEmpty()) {
                _response.value = Result(
                    success = false,
                    message = "All fields are required!"
                )
                return@launch
            }
            if (name.isEmpty()) {
                _response.value = Result(
                    success = false,
                    message = "Name cannot be empty!"
                )
                return@launch
            }
            if (code.isEmpty()) {
                _response.value = Result(
                    success = false,
                    message = "Code cannot be empty!"
                )
                return@launch
            }
            if (teacher.isEmpty()) {
                _response.value = Result(
                    success = false,
                    message = "Teacher cannot be empty!"
                )
                return@launch
            }

            val teacherDetail = getUserDetailByUsername(teacher)
            val teacherId = teacherDetail?.id
            if (teacherId == -1 || teacherDetail == null || teacherDetail.role != Role.TEACHER) {
                _response.value = Result(
                    success = false,
                    message = "Teacher not found!"
                )
                return@launch
            }

            createCourseUseCase(
                course = Course(
                    name = name,
                    code = code,
                    teacherId = teacherId,
                    image = imagePath,
                    term = term
                )
            )
            _response.value = Result(
                success = true,
                message = "Course created successfully!"
            )
            Log.d("App", "Name: $name, code: $code, teacher: $teacher")
            _name.value = ""
            _code.value = ""
            _teacher.value = ""
        }
    }
}