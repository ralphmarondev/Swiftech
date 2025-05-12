package com.ralphmarondev.swiftech.admin_features.reports.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
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
    val teacherName = viewModel.teacherName.collectAsState().value
    val courseName = viewModel.courseName.collectAsState().value
    val averageRating = viewModel.averageRating.collectAsState().value
    val ratingCounts = viewModel.ratingCounts.collectAsState().value
    val isLoading = viewModel.isLoading.collectAsState().value

    val selectedTab = viewModel.selectedTab.collectAsState().value

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
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            item {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Course Name:",
                        fontWeight = MaterialTheme.typography.labelMedium.fontWeight,
                        fontSize = MaterialTheme.typography.labelMedium.fontSize,
                        color = MaterialTheme.colorScheme.secondary
                    )
                    Text(
                        text = courseName,
                        fontWeight = MaterialTheme.typography.titleMedium.fontWeight,
                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
                    )
                    Text(
                        text = "Teacher Name:",
                        fontWeight = MaterialTheme.typography.labelMedium.fontWeight,
                        fontSize = MaterialTheme.typography.labelMedium.fontSize,
                        color = MaterialTheme.colorScheme.secondary
                    )
                    Text(
                        text = teacherName,
                        fontWeight = MaterialTheme.typography.titleMedium.fontWeight,
                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
                    )

                    Text(
                        text = "Average rating:",
                        fontWeight = MaterialTheme.typography.labelMedium.fontWeight,
                        fontSize = MaterialTheme.typography.labelMedium.fontSize,
                        color = MaterialTheme.colorScheme.secondary
                    )
                    Text(
                        text = averageRating.toString(),
                        fontWeight = MaterialTheme.typography.titleMedium.fontWeight,
                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
                    )
                }
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                )

                if (isLoading) {
                    Text(text = "Loading...")
                } else {
                    ReportBarChart(
                        ratingCounts = ratingCounts,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    )
                }
            }

            item {
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "More Information",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(16.dp)
                    )

                    val items = listOf(
                        "BY QUESTION",
                        "BY STUDENTS"
                    )

                    TabRow(
                        selectedTabIndex = selectedTab
                    ) {
                        items.forEachIndexed { index, s ->
                            Tab(
                                selected = selectedTab == index,
                                onClick = { viewModel.onSelectedTabValueChange(index) }
                            ) {
                                Text(
                                    text = s,
                                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                                    fontWeight = MaterialTheme.typography.titleMedium.fontWeight,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.padding(8.dp)
                                )
                            }
                        }
                    }

                    when (selectedTab) {
                        0 -> ReportByQuestions(viewModel)
                        1 -> ReportByStudents()
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(100.dp)) }
        }
    }
}

@Composable
private fun ReportByQuestions(
    viewModel: ReportViewModel
) {
    val questionReports = viewModel.questionReports.collectAsState().value

    questionReports.forEachIndexed { _, report ->
        OutlinedCard {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(text = report.questionText)
                Text(text = "Excellent: ${report.ratingCounts.excellent}")
                Text(text = "Very Good: ${report.ratingCounts.veryGood}")
                Text(text = "Good: ${report.ratingCounts.good}")
                Text(text = "Fair: ${report.ratingCounts.fair}")
                Text(text = "Poor: ${report.ratingCounts.poor}")
            }
        }
    }
}

@Composable
private fun ReportByStudents() {
    Text(
        text = "Report by students",
        fontSize = MaterialTheme.typography.titleMedium.fontSize,
        fontWeight = MaterialTheme.typography.titleMedium.fontWeight
    )
}