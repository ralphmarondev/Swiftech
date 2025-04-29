package com.ralphmarondev.swiftech.teacher_features.navigation

import kotlinx.serialization.Serializable

object TeacherRoutes {

    @Serializable
    data object Home

    @Serializable
    data class Reports(val courseId: Int)
}