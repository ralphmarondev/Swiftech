package com.ralphmarondev.swiftech.teacher_features.home.data.repository

import com.ralphmarondev.swiftech.core.data.local.database.dao.CourseDao
import com.ralphmarondev.swiftech.core.domain.model.Course
import com.ralphmarondev.swiftech.teacher_features.home.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow

class HomeRepositoryImpl(
    private val dao: CourseDao
): HomeRepository {
    override fun getCoursesByTeacherId(teacherId: Int): Flow<List<Course>> {
        return dao.getCoursesByTeacherId(teacherId)
    }
}