package com.ralphmarondev.swiftech.core.domain.usecases.course

import com.ralphmarondev.swiftech.core.domain.model.Course
import com.ralphmarondev.swiftech.core.domain.repositories.CourseRepository

class UpdateCourseUseCase(
    private val repository: CourseRepository
) {
    suspend operator fun invoke(course: Course) = repository.updateCourse(course)
}