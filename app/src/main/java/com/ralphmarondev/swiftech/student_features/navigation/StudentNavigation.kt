package com.ralphmarondev.swiftech.student_features.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ralphmarondev.swiftech.student_features.evaluate.presentation.EvaluateScreen
import com.ralphmarondev.swiftech.student_features.evaluation_forms.presentation.EvaluationFormScreen
import com.ralphmarondev.swiftech.student_features.home.presentation.HomeScreen
import com.ralphmarondev.swiftech.student_features.student_details.presentation.StudentDetailScreen

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
                    navController.navigate(StudentRoutes.EvaluationForms(id)) {
                        launchSingleTop = true
                    }
                },
                onLogout = onLogout,
                onAccountCardClick = { username ->
                    navController.navigate(StudentRoutes.StudentDetails(username)) {
                        launchSingleTop = true
                    }
                }
            )
        }
        composable<StudentRoutes.EvaluationForms> {
            val courseId = it.arguments?.getInt("courseId")
            EvaluationFormScreen(
                courseId = courseId ?: 0,
                navigateBack = {
                    navController.navigateUp()
                },
                onEvaluationFormClick = { formId ->
                    navController.navigate(StudentRoutes.Evaluate(formId)) {
                        launchSingleTop = true
                    }
                }
            )
        }
        composable<StudentRoutes.Evaluate> {
            val id = it.arguments?.getInt("formId")
            EvaluateScreen(
                evaluationFormId = id ?: 0,
                navigateBack = {
                    navController.navigateUp()
                }
            )
        }
        composable<StudentRoutes.StudentDetails> {
            val usernameArgs = it.arguments?.getString("username")
            StudentDetailScreen(
                usernameArgs = usernameArgs ?: "No username provided.",
                navigateBack = {
                    navController.navigateUp()
                }
            )
        }
    }
}