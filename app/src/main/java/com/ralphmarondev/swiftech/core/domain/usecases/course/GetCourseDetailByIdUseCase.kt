package com.ralphmarondev.swiftech.core.domain.usecases.course

import com.ralphmarondev.swiftech.core.domain.model.Course
import com.ralphmarondev.swiftech.core.domain.repositories.CourseRepository

class GetCourseDetailByIdUseCase(
    private val repository: CourseRepository
) {
    suspend operator fun invoke(id: Int): Course? {
        return repository.getCourseDetailById(id)
    }
}