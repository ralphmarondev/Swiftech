package com.ralphmarondev.swiftech.admin_features.evaluation.presentation.evaluation_detail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material.icons.outlined.DeleteOutline
import androidx.compose.material.icons.outlined.Update
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
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
import com.ralphmarondev.swiftech.admin_features.evaluation.presentation.components.DeleteEvaluationDialog
import com.ralphmarondev.swiftech.admin_features.evaluation.presentation.components.EvaluationResultDialog
import com.ralphmarondev.swiftech.admin_features.evaluation.presentation.components.NewQuestionDialog
import com.ralphmarondev.swiftech.admin_features.evaluation.presentation.components.UpdateQuestionDialog
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EvaluationDetailScreen(
    id: Int,
    onUpdateEvaluationDetailClick: (Int) -> Unit,
    navigateBack: () -> Unit
) {
    val viewModel: EvaluationDetailViewModel = koinViewModel(parameters = { parametersOf(id) })
    val showNewQuestionDialog = viewModel.showNewQuestionDialog.collectAsState().value
    val response = viewModel.response.collectAsState().value
    val isLoading = viewModel.isLoading.collectAsState().value
    val evaluationForm = viewModel.evaluationForm.collectAsState().value
    val questions = viewModel.questions.collectAsState().value
    val showDeleteEvaluationDialog = viewModel.showDeleteEvaluationDialog.collectAsState().value
    val showResultDialog = viewModel.showResultDialog.collectAsState().value
    val deleteResponse = viewModel.deleteResponse.collectAsState().value

    val selectedQuestion = viewModel.selectedQuestion.collectAsState().value
    val showUpdateQuestionDialog = viewModel.showUpdateQuestionDialog.collectAsState().value

    LaunchedEffect(Unit) {
        viewModel.refreshData()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Evaluation Details"
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
                        onClick = { onUpdateEvaluationDetailClick(id) }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Update,
                            contentDescription = "Update"
                        )
                    }
                    IconButton(
                        onClick = { viewModel.setShowDeleteEvaluationDialog(true) }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.DeleteOutline,
                            contentDescription = "Delete"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary
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
            contentPadding = PaddingValues(16.dp)
        ) {
            item {
                Column {
                    Text(
                        text = "Title:",
                        fontSize = MaterialTheme.typography.labelMedium.fontSize,
                        fontWeight = MaterialTheme.typography.labelMedium.fontWeight,
                        color = MaterialTheme.colorScheme.secondary
                    )
                    Text(
                        text = evaluationForm?.title ?: "No title provided.",
                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
                        fontWeight = MaterialTheme.typography.titleMedium.fontWeight,
                        color = MaterialTheme.colorScheme.secondary
                    )
                    Text(
                        text = "Description:",
                        fontSize = MaterialTheme.typography.labelMedium.fontSize,
                        fontWeight = MaterialTheme.typography.labelMedium.fontWeight,
                        color = MaterialTheme.colorScheme.secondary
                    )
                    Text(
                        text = evaluationForm?.description ?: "No description provided.",
                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
                        fontWeight = MaterialTheme.typography.titleMedium.fontWeight,
                        color = MaterialTheme.colorScheme.secondary
                    )
                    Text(
                        text = "Term:",
                        fontSize = MaterialTheme.typography.labelMedium.fontSize,
                        fontWeight = MaterialTheme.typography.labelMedium.fontWeight,
                        color = MaterialTheme.colorScheme.secondary
                    )
                    Text(
                        text = evaluationForm?.term ?: "No term provided.",
                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
                        fontWeight = MaterialTheme.typography.titleMedium.fontWeight,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                )
            }
            item {
                AnimatedVisibility(
                    visible = isLoading
                ) {
                    Text(
                        text = "Loading...",
                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
                        fontWeight = MaterialTheme.typography.titleMedium.fontWeight,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }
            items(questions) { question ->
                ElevatedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    onClick = {
                        viewModel.setSelectedQuestion(question)
                        viewModel.setShowUpdateQuestionDialog(true)
                    }
                ) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                    ) {
                        Text(
                            text = question.questionText
                        )
                    }
                }
            }
        }
    }
    if (showNewQuestionDialog) {
        NewQuestionDialog(
            onDismiss = viewModel::setShowNewQuestionDialog,
            onConfirm = viewModel::onConfirm,
            value = viewModel.newQuestion.collectAsState().value,
            onValueChange = viewModel::onNewQuestionValueChange
        )
    }
    if (showUpdateQuestionDialog) {
        UpdateQuestionDialog(
            onConfirm = { questionText ->
                viewModel.updateSelectedQuestion(questionText)
            },
            onDismiss = {
                viewModel.setShowUpdateQuestionDialog(false)
            },
            question = selectedQuestion?.questionText ?: ""
        )
    }
    if (showDeleteEvaluationDialog) {
        DeleteEvaluationDialog(
            text = "Are you sure you want to delete this evaluation form and all associated questions? This action cannot be undone.",
            onConfirm = {
                viewModel.deleteEvaluation()
            },
            onDismiss = {
                viewModel.setShowDeleteEvaluationDialog(false)
            }
        )
    }
    if (showResultDialog) {
        EvaluationResultDialog(
            result = deleteResponse,
            onConfirm = {
                viewModel.setShowResultDialog(false)
                if (deleteResponse?.success == true) {
                    navigateBack()
                }
            },
            onDismiss = {
                viewModel.setShowResultDialog(false)
            }
        )
    }
}