package com.ralphmarondev.swiftech.admin_features.evaluation.presentation.evaluation_detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material3.Card
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
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ralphmarondev.swiftech.admin_features.evaluation.presentation.components.NewQuestionDialog
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EvaluationDetailScreen(
    id: Int,
    navigateBack: () -> Unit
) {
    val viewModel: EvaluationDetailViewModel = koinViewModel(parameters = { parametersOf(id) })
    val showNewQuestionDialog = viewModel.showNewQuestionDialog.collectAsState().value
    val response = viewModel.response.collectAsState().value
    val isLoading = viewModel.isLoading.collectAsState().value
    val evaluationForm = viewModel.evaluationForm.collectAsState().value
    val questions = viewModel.questions.collectAsState().value

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Evaluation Detail"
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
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = viewModel::setShowNewQuestionDialog
            ) {
                Icon(
                    imageVector = Icons.Outlined.Add,
                    contentDescription = "New Question"
                )
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            item {
                Text(
                    text = "Evaluation detail for id: $id, title: ${evaluationForm?.title}, description: ${evaluationForm?.description}"
                )
            }
            items(questions) {
                Card(
                    modifier = Modifier
                        .padding(16.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(
                            text = it.questionText
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
}