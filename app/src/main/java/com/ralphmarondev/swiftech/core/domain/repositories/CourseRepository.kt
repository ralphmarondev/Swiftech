package com.ralphmarondev.swiftech.core.domain.repositories

import com.ralphmarondev.swiftech.core.domain.model.Course
import com.ralphmarondev.swiftech.core.domain.model.StudentCourseCrossRef
import com.ralphmarondev.swiftech.core.domain.model.User
import kotlinx.coroutines.flow.Flow

interface CourseRepository {
    suspend fun createCourse(course: Course)
    suspend fun getCourseDetailById(id: Int): Course?

    fun getAllCourses(): Flow<List<Course>>

    suspend fun insertStudentToCourse(crossRef: StudentCourseCrossRef)
    fun getStudentInCourse(id: Int): Flow<List<User>>
}