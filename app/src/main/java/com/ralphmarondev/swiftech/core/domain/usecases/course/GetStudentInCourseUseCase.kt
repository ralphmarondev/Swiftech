package com.ralphmarondev.swiftech.core.domain.usecases.course

import com.ralphmarondev.swiftech.core.domain.model.User
import com.ralphmarondev.swiftech.core.domain.repositories.CourseRepository
import kotlinx.coroutines.flow.Flow

class GetStudentInCourseUseCase(
    private val repository: CourseRepository
) {
    operator fun invoke(id: Int): Flow<List<User>> {
        return repository.getStudentInCourse(id)
    }
}