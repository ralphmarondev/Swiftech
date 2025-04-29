package com.ralphmarondev.swiftech.teacher_features.home.domain.repository

import com.ralphmarondev.swiftech.core.domain.model.Course
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    fun getCoursesByTeacherId(teacherId: Int): Flow<List<Course>>
}