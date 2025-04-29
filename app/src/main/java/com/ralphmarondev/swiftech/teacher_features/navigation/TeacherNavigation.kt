package com.ralphmarondev.swiftech.teacher_features.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ralphmarondev.swiftech.teacher_features.home.presentation.HomeScreen

@Composable
fun TeacherNavigation(
    username: String,
    onLogout: () -> Unit,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = TeacherRoutes.Home
    ) {
        composable<TeacherRoutes.Home> {
            HomeScreen(
                username = username,
                onCourseClick = {},
                onLogout = onLogout
            )
        }
    }
}