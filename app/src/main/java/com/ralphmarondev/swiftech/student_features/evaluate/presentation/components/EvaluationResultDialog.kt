package com.ralphmarondev.swiftech.student_features.evaluate.presentation.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun EvaluationResultDialog(
    resultText: String,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = onConfirm
            ) {
                Text(
                    text = "Result"
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text(text = "Cancel")
            }
        },
        text = {
            Text(
                text = resultText,
                color = MaterialTheme.colorScheme.secondary
            )
        },
        title = {
            Text(
                text = "Confirm",
                color = MaterialTheme.colorScheme.primary
            )
        }
    )
}
