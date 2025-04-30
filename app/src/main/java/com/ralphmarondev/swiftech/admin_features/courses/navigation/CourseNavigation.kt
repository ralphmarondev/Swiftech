package com.ralphmarondev.swiftech.admin_features.courses.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ralphmarondev.swiftech.admin_features.courses.presentation.course_detail.CourseDetailScreen
import com.ralphmarondev.swiftech.admin_features.courses.presentation.course_list.CourseListScreen
import com.ralphmarondev.swiftech.admin_features.courses.presentation.new_course.NewCourseScreen
import com.ralphmarondev.swiftech.admin_features.courses.presentation.update_course.UpdateCourseScreen
import kotlinx.serialization.Serializable

object CourseRoutes {

    @Serializable
    data object CourseList

    @Serializable
    data object NewCourse

    @Serializable
    data class CourseDetail(val id: Int)

    @Serializable
    data class UpdateCourse(val id: Int)
}

@Composable
fun CourseNavigation(
    navigateBack: () -> Unit,
    navigateToReports: (Int) -> Unit,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = CourseRoutes.CourseList
    ) {
        composable<CourseRoutes.CourseList> {
            CourseListScreen(
                navigateBack = navigateBack,
                onNewCourseClick = {
                    navController.navigate(CourseRoutes.NewCourse) {
                        launchSingleTop = true
                    }
                },
                onCourseClick = { id ->
                    Log.d("App", "Course navigation, id: `$id`")
                    navController.navigate(CourseRoutes.CourseDetail(id)) {
                        launchSingleTop = true
                    }
                }
            )
        }
        composable<CourseRoutes.NewCourse> {
            NewCourseScreen(
                navigateBack = {
                    navController.navigateUp()
                }
            )
        }
        composable<CourseRoutes.CourseDetail> {
            val courseId = it.arguments?.getInt("id")
            Log.d("App", "Course navigation - course detail, retrieved id: `$courseId`")
            CourseDetailScreen(
                courseId = courseId ?: -1,
                navigateBack = {
                    navController.navigateUp()
                },
                updateCourse = { id ->
                    navController.navigate(CourseRoutes.UpdateCourse(id)) {
                        launchSingleTop = true
                    }
                },
                navigateToReports = { id ->
                    navigateToReports(id)
                }
            )
        }
        composable<CourseRoutes.UpdateCourse> {
            val courseId = it.arguments?.getInt("id")
            UpdateCourseScreen(
                courseId = courseId ?: 0,
                navigateBack = {
                    navController.navigateUp()
                }
            )
        }
    }
}