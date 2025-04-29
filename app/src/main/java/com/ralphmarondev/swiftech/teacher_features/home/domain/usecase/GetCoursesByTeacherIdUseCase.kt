package com.ralphmarondev.swiftech.teacher_features.home.domain.usecase

import com.ralphmarondev.swiftech.teacher_features.home.domain.repository.HomeRepository

class GetCoursesByTeacherIdUseCase(
    private val repository: HomeRepository
) {
    operator fun invoke(teacherId: Int) = repository.getCoursesByTeacherId(teacherId)
}