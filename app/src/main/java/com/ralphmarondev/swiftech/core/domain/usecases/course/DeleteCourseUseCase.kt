package com.ralphmarondev.swiftech.core.domain.usecases.course

import com.ralphmarondev.swiftech.core.domain.repositories.CourseRepository

class DeleteCourseUseCase(
    private val repository: CourseRepository
) {
    suspend operator fun invoke(id: Int) {
        repository.deleteCourse(id)
    }
}