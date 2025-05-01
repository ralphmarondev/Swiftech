package com.ralphmarondev.swiftech.admin_features.teachers.domain.usecase

import com.ralphmarondev.swiftech.admin_features.teachers.domain.repository.TeacherRepository

class IsTeacherAssignedToAnyClassUseCase(
    private val repository: TeacherRepository
) {
    suspend operator fun invoke(teacherId: Int) =
        repository.isTeacherAssignedToAnyClass(teacherId)
}