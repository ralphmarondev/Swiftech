package com.ralphmarondev.swiftech.features.evaluation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ralphmarondev.swiftech.features.evaluation.presentation.overview.OverviewScreen
import kotlinx.serialization.Serializable

object EvaluationRoutes {
    @Serializable
    data object Overview
}

@Composable
fun EvaluationNavigation(
    navigateBack: () -> Unit,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = EvaluationRoutes.Overview
    ) {
        composable<EvaluationRoutes.Overview> {
            OverviewScreen(
                navigateBack = navigateBack
            )
        }
    }
}