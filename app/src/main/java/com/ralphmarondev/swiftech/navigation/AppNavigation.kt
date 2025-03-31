package com.ralphmarondev.swiftech.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ralphmarondev.swiftech.core.util.LocalThemeState
import com.ralphmarondev.swiftech.features.auth.presentation.login.LoginScreen
import com.ralphmarondev.swiftech.features.home.presentation.HomeScreen
import com.ralphmarondev.swiftech.features.students.presentation.new_student.NewStudentScreen
import com.ralphmarondev.swiftech.features.students.presentation.student_detail.StudentDetailScreen
import com.ralphmarondev.swiftech.features.students.presentation.student_list.StudentListScreen
import com.ralphmarondev.swiftech.features.students.presentation.update_student.UpdateStudentScreen
import com.ralphmarondev.swiftech.ui.theme.SwiftechTheme

@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController()
) {
    val themeState = LocalThemeState.current

    SwiftechTheme(
        darkTheme = themeState.darkTheme.value
    ) {
        NavHost(
            navController = navController,
            startDestination = Routes.Login
        ) {
            composable<Routes.Login> {
                LoginScreen(
                    onLoginSuccessful = { username ->
                        navController.navigate(Routes.Home(username)) {
                            launchSingleTop = true
                        }
                    }
                )
            }
            composable<Routes.Home> {
                val username = it.arguments?.getString("username")
                HomeScreen(
                    username = username ?: "No username provided.",
                    onStudentClick = {
                        navController.navigate(Routes.StudentList) {
                            launchSingleTop = true
                        }
                    },
                    onTeacherClick = {},
                    onCourseClick = {},
                    onEvaluationClick = {},
                    onLogout = {
                        navController.navigate(Routes.Login) {
                            // we are clearing everything lol
                            popUpTo(0) { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                )
            }
            composable<Routes.StudentList> {
                StudentListScreen(
                    navigateBack = {
                        navController.navigateUp()
                    },
                    onNewStudentClick = {
                        navController.navigate(Routes.NewStudent) {
                            launchSingleTop = true
                        }
                    },
                    onStudentClick = { username ->
                        navController.navigate(Routes.StudentDetail(username)) {
                            launchSingleTop = true
                        }
                    }
                )
            }
            composable<Routes.NewStudent> {
                NewStudentScreen(
                    navigateBack = {
                        navController.navigateUp()
                    }
                )
            }
            composable<Routes.StudentDetail> {
                val username = it.arguments?.getString("username")
                StudentDetailScreen(
                    navigateBack = {
                        navController.navigateUp()
                    },
                    username = username ?: "No username provided.",
                    updateStudent = {
                        navController.navigate(
                            Routes.UpdateStudent(
                                username ?: "No username provided."
                            )
                        ) {
                            launchSingleTop = true
                        }
                    }
                )
            }
            composable<Routes.UpdateStudent> {
                val username = it.arguments?.getString("username")
                UpdateStudentScreen(
                    navigateBack = {
                        navController.navigateUp()
                    },
                    usernameArgs = username ?: "No username provided."
                )
            }
        }
    }
}