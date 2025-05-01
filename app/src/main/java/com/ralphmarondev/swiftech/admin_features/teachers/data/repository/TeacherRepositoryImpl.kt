package com.ralphmarondev.swiftech.admin_features.teachers.data.repository

import com.ralphmarondev.swiftech.admin_features.teachers.domain.repository.TeacherRepository
import com.ralphmarondev.swiftech.core.data.local.database.dao.CourseDao
import kotlinx.coroutines.flow.first

class TeacherRepositoryImpl(
    private val dao: CourseDao
) : TeacherRepository {
    override suspend fun isTeacherAssignedToAnyClass(teacherId: Int): Boolean {
        val courses = dao.getCoursesByTeacherId(teacherId).first()
        return courses.isNotEmpty()
    }
}