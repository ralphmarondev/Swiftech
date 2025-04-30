package com.ralphmarondev.swiftech.admin_features.courses.presentation.update_course

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ralphmarondev.swiftech.core.domain.model.Course
import com.ralphmarondev.swiftech.core.domain.model.Result
import com.ralphmarondev.swiftech.core.domain.model.Role
import com.ralphmarondev.swiftech.core.domain.usecases.course.GetCourseDetailByIdUseCase
import com.ralphmarondev.swiftech.core.domain.usecases.course.UpdateCourseUseCase
import com.ralphmarondev.swiftech.core.domain.usecases.user.GetUserByIdUseCase
import com.ralphmarondev.swiftech.core.domain.usecases.user.GetUserDetailByUsername
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UpdateCourseViewModel(
    private val courseId: Int,
    private val getCourseDetailByIdUseCase: GetCourseDetailByIdUseCase,
    private val getUserDetailByIdUseCase: GetUserByIdUseCase,
    private val getUserDetailByUsername: GetUserDetailByUsername,
    private val updateCourseUseCase: UpdateCourseUseCase
) : ViewModel() {

    private val _name = MutableStateFlow("")
    val name = _name.asStateFlow()

    private val _code = MutableStateFlow("")
    val code = _code.asStateFlow()

    private val _term = MutableStateFlow("")
    val term = _term.asStateFlow()

    private val _teacher = MutableStateFlow("")
    val teacher = _teacher.asStateFlow()

    private val _image = MutableStateFlow("")
    val image = _image.asStateFlow()

    private val _response = MutableStateFlow<Result?>(null)
    val response = _response.asStateFlow()

    private val _showResultDialog = MutableStateFlow(false)
    val showResultDialog = _showResultDialog.asStateFlow()

    private val _courseId = MutableStateFlow(0)

    init {
        viewModelScope.launch {
            val course = getCourseDetailByIdUseCase(courseId) ?: return@launch
            val teacherDetail = getUserDetailByIdUseCase(course.teacherId ?: 0)

            _name.value = course.name
            _code.value = course.code
            _term.value = course.term
            _image.value = course.image ?: ""
            _teacher.value = teacherDetail?.username ?: "No username provided"

            _courseId.value = course.id
        }
    }

    fun onNameValueChange(value: String) {
        _name.value = value
    }

    fun onCodeValueChange(value: String) {
        _code.value = value
    }

    fun onTermValueChange(value: String) {
        _term.value = value
    }

    fun onTeacherValueChange(value: String) {
        _teacher.value = value
    }

    fun setShowResultDialog(value: Boolean) {
        _showResultDialog.value = value
    }

    fun setImageValue(value: String) {
        _image.value = value
    }

    fun updateCourse() {
        viewModelScope.launch {
            val name = _name.value.trim()
            val code = _code.value.trim()
            val term = _term.value.trim()
            val teacher = _teacher.value.trim()
            val image = _image.value.trim()

            if (name.isEmpty() && code.isEmpty() && teacher.isEmpty()) {
                _response.value = Result(
                    success = false,
                    message = "Please fill in all fields."
                )
                return@launch
            }
            if (name.isEmpty()) {
                _response.value = Result(
                    success = false,
                    message = "Class name cannot be empty!"
                )
                return@launch
            }
            if (code.isEmpty()) {
                _response.value = Result(
                    success = false,
                    message = "Class code cannot be empty!"
                )
                return@launch
            }
            if (teacher.isEmpty()) {
                _response.value = Result(
                    success = false,
                    message = "Class teacher cannot be empty!"
                )
                return@launch
            }

            try {
                val teacherDetail = getUserDetailByUsername(teacher)
                val teacherId = teacherDetail?.id
                if (teacherId == -1 || teacherDetail == null || teacherDetail.role != Role.TEACHER) {
                    _response.value = Result(
                        success = false,
                        message = "Teacher not found!"
                    )
                    return@launch
                }

                val course = Course(
                    id = _courseId.value,
                    name = name,
                    code = code,
                    term = term,
                    teacherId = teacherId,
                    image = image
                )
                updateCourseUseCase(course)
                _response.value = Result(
                    success = true,
                    message = "Course update successfully."
                )
                Log.d("App", "Name: $name, code: $code, teacher: $teacher")
                _name.value = ""
                _code.value = ""
                _teacher.value = ""
            } catch (e: Exception) {
                Log.e("App", "Updating new course failed. Error: ${e.message}")
                _response.value = Result(
                    success = false,
                    message = "Failed in updating course. Please try again later."
                )
            }
            _showResultDialog.value = true
        }
    }

    fun clearResult() {
        _response.value = null
    }
}