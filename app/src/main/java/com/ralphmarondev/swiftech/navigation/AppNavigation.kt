package com.ralphmarondev.swiftech.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ralphmarondev.swiftech.core.util.LocalThemeState
import com.ralphmarondev.swiftech.features.auth.presentation.login.LoginScreen
import com.ralphmarondev.swiftech.features.courses.navigation.CourseNavigation
import com.ralphmarondev.swiftech.features.home.presentation.HomeScreen
import com.ralphmarondev.swiftech.features.students.presentation.new_student.NewStudentScreen
import com.ralphmarondev.swiftech.features.students.presentation.student_detail.StudentDetailScreen
import com.ralphmarondev.swiftech.features.students.presentation.student_list.StudentListScreen
import com.ralphmarondev.swiftech.features.students.presentation.update_student.UpdateStudentScreen
import com.ralphmarondev.swiftech.features.teachers.presentation.new_teacher.NewTeacherScreen
import com.ralphmarondev.swiftech.features.teachers.presentation.teacher_detail.TeacherDetailScreen
import com.ralphmarondev.swiftech.features.teachers.presentation.teacher_list.TeacherListScreen
import com.ralphmarondev.swiftech.features.teachers.presentation.update_teacher.UpdateTeacherScreen
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
                    onTeacherClick = {
                        navController.navigate(Routes.TeacherList) {
                            launchSingleTop = true
                        }
                    },
                    onCourseClick = {
                        navController.navigate(Routes.CourseNavigation) {
                            launchSingleTop = true
                        }
                    },
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
            composable<Routes.TeacherList> {
                TeacherListScreen(
                    navigateBack = {
                        navController.navigateUp()
                    },
                    onNewTeacherClick = {
                        navController.navigate(Routes.NewTeacher) {
                            launchSingleTop = true
                        }
                    },
                    onTeacherClick = { username ->
                        navController.navigate(Routes.TeacherDetail(username)) {
                            launchSingleTop = true
                        }
                    }
                )
            }
            composable<Routes.NewTeacher> {
                NewTeacherScreen(
                    navigateBack = {
                        navController.navigateUp()
                    }
                )
            }
            composable<Routes.TeacherDetail> {
                val username = it.arguments?.getString("username")
                TeacherDetailScreen(
                    navigateBack = {
                        navController.navigateUp()
                    },
                    username = username ?: "No username provided.",
                    updateTeacher = {
                        navController.navigate(
                            Routes.UpdateTeacher(
                                username ?: "No username provided."
                            )
                        ) {
                            launchSingleTop = true
                        }
                    }
                )
            }
            composable<Routes.UpdateTeacher> {
                val username = it.arguments?.getString("username")
                UpdateTeacherScreen(
                    navigateBack = {
                        navController.navigateUp()
                    },
                    usernameArgs = username ?: "No username proveded."
                )
            }
            composable<Routes.CourseNavigation> {
                CourseNavigation(
                    navigateBack = {
                        navController.navigateUp()
                    }
                )
            }
        }
    }
}