package com.ralphmarondev.swiftech.core.domain.repositories

import com.ralphmarondev.swiftech.core.domain.model.Course
import kotlinx.coroutines.flow.Flow

interface CourseRepository {
    suspend fun createCourse(course: Course)

    fun getAllCourses(): Flow<List<Course>>
}