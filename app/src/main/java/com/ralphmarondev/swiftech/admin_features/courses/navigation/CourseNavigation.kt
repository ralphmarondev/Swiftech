package com.ralphmarondev.swiftech.admin_features.courses.navigation

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ralphmarondev.swiftech.admin_features.courses.presentation.course_detail.CourseDetailScreen
import com.ralphmarondev.swiftech.admin_features.courses.presentation.course_list.CourseListScreen
import com.ralphmarondev.swiftech.admin_features.courses.presentation.new_course.NewCourseScreen
import kotlinx.serialization.Serializable

object CourseRoutes {

    @Serializable
    data object CourseList

    @Serializable
    data object NewCourse

    @Serializable
    data class CourseDetail(val id: Int)
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
            val context = LocalContext.current
            CourseDetailScreen(
                courseId = courseId ?: -1,
                navigateBack = {
                    navController.navigateUp()
                },
                updateCourse = { id ->
                    Log.d("App", "Updating course with id: $id")
                    Toast.makeText(
                        context,
                        "Updating course with id: $id",
                        Toast.LENGTH_SHORT
                    ).show()
                },
                navigateToReports = { id ->
                    navigateToReports(id)
                }
            )
        }
    }
}