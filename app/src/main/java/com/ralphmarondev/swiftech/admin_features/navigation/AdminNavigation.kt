package com.ralphmarondev.swiftech.admin_features.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ralphmarondev.swiftech.admin_features.courses.navigation.CourseNavigation
import com.ralphmarondev.swiftech.admin_features.evaluation.navigation.EvaluationNavigation
import com.ralphmarondev.swiftech.admin_features.home.presentation.HomeScreen
import com.ralphmarondev.swiftech.admin_features.profile.presentation.ProfileScreen
import com.ralphmarondev.swiftech.admin_features.reports.presentation.ReportScreen
import com.ralphmarondev.swiftech.admin_features.students.presentation.new_student.NewStudentScreen
import com.ralphmarondev.swiftech.admin_features.students.presentation.student_detail.StudentDetailScreen
import com.ralphmarondev.swiftech.admin_features.students.presentation.student_list.StudentListScreen
import com.ralphmarondev.swiftech.admin_features.students.presentation.update_student.UpdateStudentScreen
import com.ralphmarondev.swiftech.admin_features.teachers.presentation.new_teacher.NewTeacherScreen
import com.ralphmarondev.swiftech.admin_features.teachers.presentation.teacher_detail.TeacherDetailScreen
import com.ralphmarondev.swiftech.admin_features.teachers.presentation.teacher_list.TeacherListScreen
import com.ralphmarondev.swiftech.admin_features.teachers.presentation.update_teacher.UpdateTeacherScreen

@Composable
fun AdminNavigation(
    username: String,
    onLogout: () -> Unit,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = AdminRoutes.Home
    ) {
        composable<AdminRoutes.Home> {
            HomeScreen(
                username = username,
                onStudentClick = {
                    navController.navigate(AdminRoutes.StudentList) {
                        launchSingleTop = true
                    }
                },
                onTeacherClick = {
                    navController.navigate(AdminRoutes.TeacherList) {
                        launchSingleTop = true
                    }
                },
                onCourseClick = {
                    navController.navigate(AdminRoutes.CourseNavigation) {
                        launchSingleTop = true
                    }
                },
                onEvaluationClick = {
                    navController.navigate(AdminRoutes.EvaluationNavigation) {
                        launchSingleTop = true
                    }
                },
                onAccountCardClick = { username ->
                    navController.navigate(AdminRoutes.Profile(username)) {
                        launchSingleTop = true
                    }
                },
                onLogout = onLogout
            )
        }
        composable<AdminRoutes.StudentList> {
            StudentListScreen(
                navigateBack = {
                    navController.navigateUp()
                },
                onNewStudentClick = {
                    navController.navigate(AdminRoutes.NewStudent) {
                        launchSingleTop = true
                    }
                },
                onStudentClick = { username ->
                    navController.navigate(AdminRoutes.StudentDetail(username)) {
                        launchSingleTop = true
                    }
                }
            )
        }
        composable<AdminRoutes.NewStudent> {
            NewStudentScreen(
                navigateBack = {
                    navController.navigateUp()
                }
            )
        }
        composable<AdminRoutes.StudentDetail> {
            val username = it.arguments?.getString("username")
            StudentDetailScreen(
                navigateBack = {
                    navController.navigateUp()
                },
                username = username ?: "No username provided.",
                updateStudent = {
                    navController.navigate(
                        AdminRoutes.UpdateStudent(
                            username ?: "No username provided."
                        )
                    ) {
                        launchSingleTop = true
                    }
                }
            )
        }
        composable<AdminRoutes.UpdateStudent> {
            val username = it.arguments?.getString("username")
            UpdateStudentScreen(
                navigateBack = {
                    navController.navigateUp()
                },
                usernameArgs = username ?: "No username provided."
            )
        }
        composable<AdminRoutes.TeacherList> {
            TeacherListScreen(
                navigateBack = {
                    navController.navigateUp()
                },
                onNewTeacherClick = {
                    navController.navigate(AdminRoutes.NewTeacher) {
                        launchSingleTop = true
                    }
                },
                onTeacherClick = { username ->
                    navController.navigate(AdminRoutes.TeacherDetail(username)) {
                        launchSingleTop = true
                    }
                }
            )
        }
        composable<AdminRoutes.NewTeacher> {
            NewTeacherScreen(
                navigateBack = {
                    navController.navigateUp()
                }
            )
        }
        composable<AdminRoutes.TeacherDetail> {
            val username = it.arguments?.getString("username")
            TeacherDetailScreen(
                navigateBack = {
                    navController.navigateUp()
                },
                username = username ?: "No username provided.",
                updateTeacher = {
                    navController.navigate(
                        AdminRoutes.UpdateTeacher(
                            username ?: "No username provided."
                        )
                    ) {
                        launchSingleTop = true
                    }
                }
            )
        }
        composable<AdminRoutes.UpdateTeacher> {
            val username = it.arguments?.getString("username")
            UpdateTeacherScreen(
                navigateBack = {
                    navController.navigateUp()
                },
                usernameArgs = username ?: "No username proveded."
            )
        }
        composable<AdminRoutes.CourseNavigation> {
            CourseNavigation(
                navigateBack = {
                    navController.navigateUp()
                },
                navigateToReports = { courseId ->
                    navController.navigate(AdminRoutes.Report(courseId)) {
                        launchSingleTop = true
                    }
                }
            )
        }
        composable<AdminRoutes.EvaluationNavigation> {
            EvaluationNavigation(
                navigateBack = {
                    navController.navigateUp()
                }
            )
        }
        composable<AdminRoutes.Report> {
            val courseId = it.arguments?.getInt("courseId")
            Log.d("App", "AdminNavigation, courseId: `$courseId`")
            ReportScreen(
                courseId = courseId ?: 0,
                navigateBack = {
                    navController.navigateUp()
                }
            )
        }
        composable<AdminRoutes.Profile> {
            val usernameArgs = it.arguments?.getString("username")
            ProfileScreen(
                usernameArgs = usernameArgs ?: "No username provided.",
                navigateBack = {
                    navController.navigateUp()
                }
            )
        }
    }
}