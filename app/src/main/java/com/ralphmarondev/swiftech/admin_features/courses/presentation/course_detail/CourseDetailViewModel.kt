package com.ralphmarondev.swiftech.admin_features.courses.presentation.course_detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ralphmarondev.swiftech.core.domain.model.Course
import com.ralphmarondev.swiftech.core.domain.model.Result
import com.ralphmarondev.swiftech.core.domain.model.Role
import com.ralphmarondev.swiftech.core.domain.model.StudentCourseCrossRef
import com.ralphmarondev.swiftech.core.domain.model.User
import com.ralphmarondev.swiftech.core.domain.usecases.course.DeleteCourseUseCase
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
    private val getStudentInCourseUseCase: GetStudentInCourseUseCase,
    private val deleteCourseUseCase: DeleteCourseUseCase
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

    private val _selectedStudent = MutableStateFlow<User?>(null)
    val selectedStudent = _selectedStudent.asStateFlow()

    private val _removeStudentDialog = MutableStateFlow(false)
    val removeStudentDialog = _removeStudentDialog.asStateFlow()

    private val _removeStudentResponse = MutableStateFlow<Result?>(null)
    val removeStudentResponse = _removeStudentResponse

    private val _showDeleteCourseDialog = MutableStateFlow(false)
    val showDeleteCourseDialog = _showDeleteCourseDialog.asStateFlow()

    private val _showResultDialog = MutableStateFlow(false)
    val showResultDialog = _showResultDialog.asStateFlow()

    private val _deleteResponse = MutableStateFlow<Result?>(null)
    val deleteResponse = _deleteResponse.asStateFlow()

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
        _students.value = emptyList()
        getStudentInCourseUseCase(courseId).collect { students ->
            _students.value = students
        }
    }

    fun refreshCourse() {
        viewModelScope.launch {
            val course = getCourseDetailByIdUseCase(courseId)
            _courseDetail.value = course
            val teacher = getUserByIdUseCase(course?.teacherId ?: 0)
            _teacherDetail.value = teacher
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

    fun onStudentClick(student: User) {
        _selectedStudent.value = student
    }

    fun setRemoveStudentDialog(value: Boolean) {
        _removeStudentDialog.value = value
    }

    // TODO: IMPLEMENT THIS!
    fun removeStudentInClass() {
        viewModelScope.launch {
            val student = _selectedStudent.value
            Log.d("App", "Removing ${student?.fullName} from the class...")

            _removeStudentResponse.value = Result(
                success = true,
                message = "Successfully removed student."
            )
        }
    }

    fun setShowDeleteCourseDialog(value: Boolean) {
        _showDeleteCourseDialog.value = value
    }

    fun setShowResultDialog(value: Boolean) {
        _showResultDialog.value = value
    }

    fun deleteCourse() {
        Log.d("App", "Deleting course with id: `$courseId`, name: `${_courseDetail.value?.name}`")
        viewModelScope.launch {
            try {
                _showDeleteCourseDialog.value = false
                deleteCourseUseCase(courseId)
                _deleteResponse.value = Result(
                    success = true,
                    message = "Successfully deleted course."
                )
            } catch (e: Exception) {
                Log.e("App", "Error deleting course: ${e.message}")
                _deleteResponse.value = Result(
                    success = false,
                    message = "Error deleting course."
                )
            }
            _showResultDialog.value = true
        }
    }
}