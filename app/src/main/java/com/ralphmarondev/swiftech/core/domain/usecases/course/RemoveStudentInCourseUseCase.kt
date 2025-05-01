package com.ralphmarondev.swiftech.core.domain.usecases.course

import com.ralphmarondev.swiftech.core.domain.repositories.CourseRepository

class RemoveStudentInCourseUseCase(
    private val repository: CourseRepository
) {
    suspend operator fun invoke(
        courseId: Int,
        studentId: Int
    ) = repository.removeStudentInCourse(
        courseId = courseId,
        studentId = studentId
    )
}