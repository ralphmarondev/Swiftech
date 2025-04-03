package com.ralphmarondev.swiftech.features.courses.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ralphmarondev.swiftech.features.courses.presentation.course_list.CourseListScreen
import kotlinx.serialization.Serializable

object CourseRoutes {

    @Serializable
    data object CourseList
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
                onNewCourseClick = {},
                onCourseClick = {}
            )
        }
    }
}