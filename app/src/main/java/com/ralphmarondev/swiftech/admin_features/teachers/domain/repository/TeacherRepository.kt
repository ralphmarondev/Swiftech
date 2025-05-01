package com.ralphmarondev.swiftech.admin_features.teachers.domain.repository

interface TeacherRepository {
    suspend fun isTeacherAssignedToAnyClass(teacherId: Int): Boolean
}