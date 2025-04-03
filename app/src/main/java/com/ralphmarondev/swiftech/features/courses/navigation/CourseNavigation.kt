package com.ralphmarondev.swiftech.features.courses.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ralphmarondev.swiftech.features.courses.presentation.course_list.CourseListScreen
import com.ralphmarondev.swiftech.features.courses.presentation.new_course.NewCourseScreen
import kotlinx.serialization.Serializable

object CourseRoutes {

    @Serializable
    data object CourseList

    @Serializable
    data object NewCourse
}

@Composable
fun CourseNavigation(
    navigateBack: () -> Unit,
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
                onCourseClick = {}
            )
        }
        composable<CourseRoutes.NewCourse> {
            NewCourseScreen(
                navigateBack = {
                    navController.navigateUp()
                }
            )
        }
    }
}