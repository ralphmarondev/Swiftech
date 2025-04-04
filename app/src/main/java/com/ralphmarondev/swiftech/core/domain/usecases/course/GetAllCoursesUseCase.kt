package com.ralphmarondev.swiftech.core.domain.usecases.course

import com.ralphmarondev.swiftech.core.domain.model.Course
import com.ralphmarondev.swiftech.core.domain.repositories.CourseRepository
import kotlinx.coroutines.flow.Flow

class GetAllCoursesUseCase(
    private val repository: CourseRepository
) {
    operator fun invoke(): Flow<List<Course>> {
        return repository.getAllCourses()
    }
}