package com.ralphmarondev.swiftech.student_features.evaluate.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ralphmarondev.swiftech.student_features.evaluate.presentation.components.EvaluationResultDialog
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EvaluateScreen(
    id: Int,
    navigateBack: () -> Unit
) {
    val viewModel: EvaluateViewModel = koinViewModel(parameters = { parametersOf(id) })
    val courseName = viewModel.courseName.collectAsState().value
    val courseTeacher = viewModel.courseTeacher.collectAsState().value
    val questions = viewModel.questions.collectAsState().value
    val hasEvaluated = viewModel.hasEvaluated.collectAsState().value
    val showEvaluationResultDialog = viewModel.showEvaluationResultDialog.collectAsState().value

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Evaluate"
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
                .padding(innerPadding),
            contentPadding = PaddingValues(16.dp)
        ) {
            item {
                Column {
                    Text(
                        text = "Course Name:",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W300,
                        color = MaterialTheme.colorScheme.secondary
                    )
                    Text(
                        text = courseName,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W500,
                        color = MaterialTheme.colorScheme.secondary
                    )
                    Text(
                        text = "Teacher:",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W300,
                        color = MaterialTheme.colorScheme.secondary
                    )
                    Text(
                        text = courseTeacher,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W500,
                        color = MaterialTheme.colorScheme.secondary
                    )
                    HorizontalDivider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp)
                    )
                }
            }
            item {
                AnimatedVisibility(visible = hasEvaluated && questions.isEmpty()) {
                    Text(
                        text = "You evaluated already."
                    )
                }
            }
            items(questions) { questionRating ->
                ElevatedCard(
                    modifier = Modifier
                        .padding(vertical = 4.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        Text(
                            text = questionRating.question,
                            fontSize = MaterialTheme.typography.titleMedium.fontSize,
                            fontWeight = MaterialTheme.typography.titleMedium.fontWeight,
                            color = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )

                        val ratings = listOf(
                            "Excellent",
                            "Very Good",
                            "Good",
                            "Fair",
                            "Poor"
                        )

                        ratings.forEach { rating ->
                            RatingBox(
                                label = rating,
                                value = questionRating.selectedRating == rating,
                                onValueChanged = { isChecked ->
                                    if (isChecked) {
                                        viewModel.selectRating(questionRating.question, rating)
                                    }
                                }
                            )
                        }
                    }
                }
            }
            item {
                AnimatedVisibility(!hasEvaluated) {
                    Button(
                        onClick = viewModel::showEvaluationResultDialogValueChange,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp, horizontal = 6.dp),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "SUBMIT"
                        )
                    }
                }
            }
            item { Spacer(modifier = Modifier.height(100.dp)) }
        }
    }

    if (showEvaluationResultDialog) {
        EvaluationResultDialog(
            resultText = "Evaluation submitted successfully.",
            onDismiss = viewModel::showEvaluationResultDialogValueChange,
            onConfirm = {
                viewModel.submitEvaluation()
                viewModel.showEvaluationResultDialogValueChange()
            }
        )
    }
}

@Composable
private fun RatingBox(
    label: String,
    value: Boolean,
    onValueChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Checkbox(
            checked = value,
            onCheckedChange = onValueChanged
        )

        Text(
            text = label,
            color = MaterialTheme.colorScheme.secondary
        )
    }
}