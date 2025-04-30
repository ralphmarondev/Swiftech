package com.ralphmarondev.swiftech.admin_features.evaluation.presentation.new_evaluation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.SaveAlt
import androidx.compose.material.icons.outlined.Timeline
import androidx.compose.material.icons.outlined.Title
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ralphmarondev.swiftech.admin_features.evaluation.presentation.components.EvaluationResultDialog
import com.ralphmarondev.swiftech.admin_features.evaluation.presentation.components.NewQuestionDialog
import com.ralphmarondev.swiftech.admin_features.evaluation.presentation.components.SaveEvaluationDialog
import com.ralphmarondev.swiftech.core.presentation.NormalTextField
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewEvaluationScreen(
    navigateBack: () -> Unit
) {
    val viewModel: NewEvaluationViewModel = koinViewModel()
    val showNewQuestionDialog = viewModel.showNewQuestionDialog.collectAsState().value
    val title = viewModel.title.collectAsState().value
    val description = viewModel.description.collectAsState().value
    val term = viewModel.term.collectAsState().value
    val questions = viewModel.questions.collectAsState().value
    val formResponse = viewModel.formResponse.collectAsState().value

    val showSaveEvaluationDialog = viewModel.showSaveEvaluationDialog.collectAsState().value
    val showEvaluationResultDialog = viewModel.showEvaluationResultDialog.collectAsState().value

    LaunchedEffect(formResponse) {
        if (formResponse?.success == true) {
            navigateBack()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "New Evaluation"
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
                actions = {
                    IconButton(
                        onClick = viewModel::onSave
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.SaveAlt,
                            contentDescription = "Save"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = viewModel::setShowNewQuestionDialog
            ) {
                Icon(
                    imageVector = Icons.Outlined.Add,
                    contentDescription = "New question"
                )
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(horizontal = 8.dp)
        ) {
            item { Spacer(modifier = Modifier.height(16.dp)) }
            item {
                NormalTextField(
                    value = title,
                    onValueChange = viewModel::onTitleValueChange,
                    label = "Title",
                    placeholder = "Evaluation for cuties.",
                    leadingIcon = Icons.Outlined.Title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                )
                NormalTextField(
                    value = description,
                    onValueChange = viewModel::onDescriptionValueChange,
                    label = "Description",
                    placeholder = "For cuties only.",
                    leadingIcon = Icons.Outlined.Description,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                )
                NormalTextField(
                    value = term,
                    onValueChange = viewModel::onTermValueChange,
                    label = "Term",
                    placeholder = "24-25-1",
                    leadingIcon = Icons.Outlined.Timeline,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                )

                Text(
                    text = "Questions:",
                    modifier = Modifier.padding(start = 8.dp, top = 16.dp),
                    fontWeight = MaterialTheme.typography.titleMedium.fontWeight,
                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                    color = MaterialTheme.colorScheme.secondary
                )
                AnimatedVisibility(visible = questions.isEmpty()) {
                    Text(
                        text = "No questions yet.",
                        modifier = Modifier.padding(16.dp),
                        fontWeight = MaterialTheme.typography.titleMedium.fontWeight,
                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }
            items(questions) { question ->
                ElevatedCard(
                    onClick = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                    ) {
                        Text(
                            text = question
                        )
                    }
                }
            }
            item { Spacer(modifier = Modifier.height(100.dp)) }
        }
    }

    if (showNewQuestionDialog) {
        NewQuestionDialog(
            onConfirm = viewModel::onConfirm,
            onDismiss = viewModel::setShowNewQuestionDialog,
            value = viewModel.newQuestion.collectAsState().value,
            onValueChange = viewModel::onNewQuestionValueChange
        )
    }

    if (showSaveEvaluationDialog) {
        SaveEvaluationDialog(
            onDismiss = {

            },
            onConfirm = {

            },
            text = "Save you evaluation form now? Press cancel to add more questions."
        )
    }

    if (showEvaluationResultDialog) {
        EvaluationResultDialog(
            onDismiss = {

            },
            result = formResponse
        )
    }
}