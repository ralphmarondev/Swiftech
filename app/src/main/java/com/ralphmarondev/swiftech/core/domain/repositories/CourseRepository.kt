package com.ralphmarondev.swiftech.core.domain.repositories

import com.ralphmarondev.swiftech.core.domain.model.Course
import com.ralphmarondev.swiftech.core.domain.model.StudentCourseCrossRef
import com.ralphmarondev.swiftech.core.domain.model.User
import kotlinx.coroutines.flow.Flow

interface CourseRepository {
    suspend fun createCourse(course: Course)
    suspend fun updateCourse(course: Course)
    suspend fun deleteCourse(id: Int)
    suspend fun getCourseDetailById(id: Int): Course?

    fun getAllCourses(): Flow<List<Course>>

    suspend fun insertStudentToCourse(crossRef: StudentCourseCrossRef)
    suspend fun removeStudentInCourse(courseId: Int, studentId: Int)
    fun getStudentInCourse(id: Int): Flow<List<User>>

    fun getCoursesForStudent(studentId: Int): Flow<List<Course>>
}