package com.ralphmarondev.swiftech.admin_features.navigation

import kotlinx.serialization.Serializable

object AdminRoutes {

    @Serializable
    data object Home

    @Serializable
    data object StudentList

    @Serializable
    data object NewStudent

    @Serializable
    data class StudentDetail(val username: String)

    @Serializable
    data class UpdateStudent(val username: String)

    @Serializable
    data object TeacherList

    @Serializable
    data object NewTeacher

    @Serializable
    data class TeacherDetail(val username: String)

    @Serializable
    data class UpdateTeacher(val username: String)

    @Serializable
    data object CourseNavigation

    @Serializable
    data object EvaluationNavigation

    @Serializable
    data class Report(val courseId: Int)

    @Serializable
    data class Profile(val username: String)
}