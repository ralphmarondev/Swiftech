package com.ralphmarondev.swiftech.navigation

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.ralphmarondev.swiftech.student_features.navigation.StudentNavigation
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
                    }
                )
            }
            composable<Routes.Teacher> {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Teacher"
                    )
                }
            }
        }
    }
}