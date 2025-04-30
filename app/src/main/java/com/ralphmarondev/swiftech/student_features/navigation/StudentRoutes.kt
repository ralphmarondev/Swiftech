package com.ralphmarondev.swiftech.student_features.navigation

import kotlinx.serialization.Serializable

object StudentRoutes {

    @Serializable
    data object Home

    @Serializable
    data class EvaluationForms(val courseId: Int)

    @Serializable
    data class Evaluate(val formId: Int)

    @Serializable
    data class StudentDetails(val username: String)
}