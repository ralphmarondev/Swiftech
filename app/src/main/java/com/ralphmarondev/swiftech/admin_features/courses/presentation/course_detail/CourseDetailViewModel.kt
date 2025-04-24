package com.ralphmarondev.swiftech.admin_features.courses.presentation.course_detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ralphmarondev.swiftech.core.domain.model.Course
import com.ralphmarondev.swiftech.core.domain.model.Result
import com.ralphmarondev.swiftech.core.domain.model.Role
import com.ralphmarondev.swiftech.core.domain.model.StudentCourseCrossRef
import com.ralphmarondev.swiftech.core.domain.model.User
import com.ralphmarondev.swiftech.core.domain.usecases.course.GetCourseDetailByIdUseCase
import com.ralphmarondev.swiftech.core.domain.usecases.course.GetStudentInCourseUseCase
import com.ralphmarondev.swiftech.core.domain.usecases.course.InsertStudentToCourseUseCase
import com.ralphmarondev.swiftech.core.domain.usecases.user.GetUserByIdUseCase
import com.ralphmarondev.swiftech.core.domain.usecases.user.GetUserDetailByUsername
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CourseDetailViewModel(
    private val courseId: Int,
    private val getCourseDetailByIdUseCase: GetCourseDetailByIdUseCase,
    private val getUserByIdUseCase: GetUserByIdUseCase,
    private val getUserDetailByUsername: GetUserDetailByUsername,
    private val insertStudentToCourseUseCase: InsertStudentToCourseUseCase,
    private val getStudentInCourseUseCase: GetStudentInCourseUseCase
) : ViewModel() {

    private val _courseDetail = MutableStateFlow<Course?>(null)
    val courseDetail = _courseDetail.asStateFlow()

    private val _teacherDetail = MutableStateFlow<User?>(null)
    val teacherDetail = _teacherDetail.asStateFlow()

    private val _students = MutableStateFlow<List<User>>(emptyList())
    val students = _students.asStateFlow()

    private val _showEnrollStudentDialog = MutableStateFlow(false)
    val showEnrollStudentDialog = _showEnrollStudentDialog.asStateFlow()

    private val _studentToEnroll = MutableStateFlow("")
    val studentToEnroll = _studentToEnroll.asStateFlow()

    private val _response = MutableStateFlow<Result?>(null)
    val response = _response.asStateFlow()

    init {
        viewModelScope.launch {
            val course = getCourseDetailByIdUseCase(courseId)
            _courseDetail.value = course
            val teacher = getUserByIdUseCase(course?.teacherId ?: 0)
            _teacherDetail.value = teacher
            getStudentsInCourse()
        }
    }

    private suspend fun getStudentsInCourse() {
        getStudentInCourseUseCase(courseId).collect { students ->
            _students.value = students
        }
    }

    fun onUsernameValueChange(value: String) {
        _studentToEnroll.value = value
    }

    fun setShowEnrollStudentDialog() {
        _showEnrollStudentDialog.value = !_showEnrollStudentDialog.value
    }

    fun enrollStudent() {
        viewModelScope.launch {
            val username = _studentToEnroll.value.trim()

            if (username.isEmpty()) {
                _response.value = Result(
                    success = false,
                    message = "Username cannot be empty."
                )
                return@launch
            }
            val user = getUserDetailByUsername(username)

            if (user == null || user.role != Role.STUDENT) {
                _response.value = Result(
                    success = false,
                    message = "Student not found."
                )
                return@launch
            }

            insertStudentToCourseUseCase(
                crossRef = StudentCourseCrossRef(
                    studentId = user.id,
                    courseId = courseId
                )
            )

            _response.value = Result(
                success = true,
                message = "Successfully enrolled student."
            )
            Log.d("App", "Enrolling student with username: $username")
            _studentToEnroll.value = ""
            delay(1500)
            setShowEnrollStudentDialog()
            getStudentsInCourse()
        }
    }
}