package com.ralphmarondev.swiftech.core.data.repositories

import com.ralphmarondev.swiftech.core.data.local.database.dao.CourseDao
import com.ralphmarondev.swiftech.core.domain.model.Course
import com.ralphmarondev.swiftech.core.domain.repositories.CourseRepository
import kotlinx.coroutines.flow.Flow

class CourseRepositoryImpl(
    private val courseDao: CourseDao
) : CourseRepository {

    override suspend fun createCourse(course: Course) {
        courseDao.createCourse(course)
    }

    override suspend fun getCourseDetailById(id: Int): Course? {
        return courseDao.getCourseDetailById(id)
    }

    override fun getAllCourses(): Flow<List<Course>> {
        return courseDao.getAllCourses()
    }
}