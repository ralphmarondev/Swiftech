package com.ralphmarondev.swiftech.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ralphmarondev.swiftech.admin_features.navigation.AdminNavigation
import com.ralphmarondev.swiftech.auth.presentation.login.LoginScreen
import com.ralphmarondev.swiftech.core.data.local.preferences.AppPreferences
import com.ralphmarondev.swiftech.core.domain.model.Role
import com.ralphmarondev.swiftech.core.util.LocalThemeState
import com.ralphmarondev.swiftech.settings.presentation.SettingScreen
import com.ralphmarondev.swiftech.student_features.navigation.StudentNavigation
import com.ralphmarondev.swiftech.teacher_features.navigation.TeacherNavigation
import com.ralphmarondev.swiftech.ui.theme.SwiftechTheme

@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController()
) {
    val themeState = LocalThemeState.current
    val context = LocalContext.current
    val preferences = AppPreferences(context)

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
                        val role = preferences.getRole() ?: "Invalid role."
                        Log.d("App", "Username: `$username`, role: `$role`")
                        when (role) {
                            Role.ADMINISTRATOR -> {
                                Log.d("App", "Inside admin block")
                                navController.navigate(Routes.Admin(username)) {
                                    popUpTo(0) { inclusive = true }
                                    launchSingleTop = true
                                }
                            }

                            Role.STUDENT -> {
                                Log.d("App", "Inside student block")
                                navController.navigate(Routes.Student(username)) {
                                    popUpTo(0) { inclusive = true }
                                    launchSingleTop = true
                                }
                            }

                            Role.TEACHER -> {
                                Log.d("App", "Inside teacher block")
                                navController.navigate(Routes.Teacher(username)) {
                                    popUpTo(0) { inclusive = true }
                                    launchSingleTop = true
                                }
                            }
                        }
                    }
                )
            }
            composable<Routes.Admin> {
                val username = it.arguments?.getString("username")
                AdminNavigation(
                    username = username ?: "No username provided.",
                    onLogout = {
                        navController.navigate(Routes.Login) {
                            popUpTo(0) { inclusive = true }
                            launchSingleTop = true
                        }
                    },
                    navigateToSettings = {
                        navController.navigate(Routes.Settings) {
                            launchSingleTop = true
                        }
                    }
                )
            }
            composable<Routes.Student> {
                val username = it.arguments?.getString("username")
                StudentNavigation(
                    username = username ?: "No username provided.",
                    onLogout = {
                        navController.navigate(Routes.Login) {
                            popUpTo(0) { inclusive = true }
                            launchSingleTop = true
                        }
                    },
                    navigateToSettings = {
                        navController.navigate(Routes.Settings) {
                            launchSingleTop = true
                        }
                    }
                )
            }
            composable<Routes.Teacher> {
                val username = it.arguments?.getString("username")
                TeacherNavigation(
                    username = username ?: "No username provided.",
                    onLogout = {
                        navController.navigate(Routes.Login) {
                            popUpTo(0) { inclusive = true }
                            launchSingleTop = true
                        }
                    },
                    navigateToSettings = {
                        navController.navigate(Routes.Settings) {
                            launchSingleTop = true
                        }
                    }
                )
            }
            composable<Routes.Settings> {
                SettingScreen(
                    navigateBack = {
                        navController.navigateUp()
                    }
                )
            }
        }
    }
}