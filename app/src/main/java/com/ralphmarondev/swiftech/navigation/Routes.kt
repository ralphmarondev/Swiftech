package com.ralphmarondev.swiftech.navigation

import kotlinx.serialization.Serializable

object Routes {

    @Serializable
    data object Login

    @Serializable
    data class Admin(val username: String)

    @Serializable
    data class Student(val username: String)

    @Serializable
    data class Teacher(val username: String)
}