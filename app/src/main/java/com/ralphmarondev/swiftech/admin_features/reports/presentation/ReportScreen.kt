package com.ralphmarondev.swiftech.admin_features.reports.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ralphmarondev.swiftech.admin_features.reports.presentation.components.ReportBarChart
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportScreen(
    courseId: Int,
    navigateBack: () -> Unit
) {
    val viewModel: ReportViewModel = koinViewModel(parameters = { parametersOf(courseId) })
    val averageRating = viewModel.averageRating.collectAsState().value
    val ratingCounts = viewModel.ratingCounts.collectAsState().value

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Reports"
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = navigateBack
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.ArrowBackIosNew,
                            contentDescription = "Navigate back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Text(
                text = "Teacher Evaluation Report",
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = "Course id: `$courseId`"
            )
            Text(
                text = "Average rating: $averageRating"
            )

            Text(text = "Excellent: ${ratingCounts.excellent}")
            Text(text = "Very good: ${ratingCounts.veryGood}")
            Text(text = "Good: ${ratingCounts.good}")
            Text(text = "Fair: ${ratingCounts.fair}")
            Text(text = "Poor: ${ratingCounts.poor}")

            Spacer(modifier = Modifier.height(24.dp))
            ReportBarChart(ratingCounts)
        }
    }
}