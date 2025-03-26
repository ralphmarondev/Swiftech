package com.ralphmarondev.swiftech.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ralphmarondev.swiftech.core.data.local.preferences.AppPreferences
import com.ralphmarondev.swiftech.core.util.LocalThemeState
import com.ralphmarondev.swiftech.features.auth.presentation.login.LoginScreen
import com.ralphmarondev.swiftech.features.home.presentation.HomeScreen
import com.ralphmarondev.swiftech.ui.theme.SwiftechTheme

@Composable
fun AppNavigation(
    preferences: AppPreferences,
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
                    onLoginSuccessful = {
                        navController.navigate(Routes.Home) {
                            popUpTo<Routes.Login> { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                )
            }
            composable<Routes.Home> {
                HomeScreen()
            }
        }
    }
}