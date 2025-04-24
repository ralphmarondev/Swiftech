package com.ralphmarondev.swiftech.student_features.navigation

import kotlinx.serialization.Serializable

object StudentRoutes {

    @Serializable
    data object Home

    @Serializable
    data class Evaluate(val id: Int)
}