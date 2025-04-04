package com.ralphmarondev.swiftech.core.domain.usecases.course

import android.util.Log
import com.ralphmarondev.swiftech.core.domain.model.Course
import com.ralphmarondev.swiftech.core.domain.repositories.CourseRepository

class CreateCourseUseCase(
    private val repository: CourseRepository
) {
    suspend operator fun invoke(course: Course) {
        try {
            repository.createCourse(course)
            Log.d("App", "Course ${course.name} created successfully.")
        } catch (e: Exception) {
            Log.e("App", "Creating course error: ${e.message}")
        }
    }
}