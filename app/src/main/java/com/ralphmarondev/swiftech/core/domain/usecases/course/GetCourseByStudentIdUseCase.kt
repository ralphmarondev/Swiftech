package com.ralphmarondev.swiftech.core.domain.usecases.course

import com.ralphmarondev.swiftech.core.domain.repositories.CourseRepository

class GetCourseByStudentIdUseCase(
    private val repository: CourseRepository
) {
    operator fun invoke(studentId: Int) = repository.getCousesForStudent(studentId)
}