package com.ralphmarondev.swiftech.student_features.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ralphmarondev.swiftech.student_features.evaluate.presentation.EvaluateScreen
import com.ralphmarondev.swiftech.student_features.evaluation_forms.presentation.EvaluationFormScreen
import com.ralphmarondev.swiftech.student_features.home.presentation.HomeScreen

@Composable
fun StudentNavigation(
    username: String,
    onLogout: () -> Unit,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = StudentRoutes.Home
    ) {
        composable<StudentRoutes.Home> {
            HomeScreen(
                username = username,
                onCourseClick = { id ->
                    navController.navigate(StudentRoutes.Evaluate(id)) {
                        launchSingleTop = true
                    }
                },
                onLogout = onLogout
            )
        }
        composable<StudentRoutes.EvaluationForms> {
            val courseId = it.arguments?.getInt("courseId")
            EvaluationFormScreen(
                courseId = courseId ?: 0,
                navigateBack = {
                    navController.navigateUp()
                }
            )
        }
        composable<StudentRoutes.Evaluate> {
            val id = it.arguments?.getInt("formId")
            EvaluateScreen(
                id = id ?: 0,
                navigateBack = {
                    navController.navigateUp()
                }
            )
        }
    }
}