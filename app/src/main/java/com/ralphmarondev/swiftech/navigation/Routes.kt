package com.ralphmarondev.swiftech.navigation

import kotlinx.serialization.Serializable

object Routes {

    @Serializable
    data object Login

    @Serializable
    data class Home(val username: String)

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
}