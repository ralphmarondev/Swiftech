package com.ralphmarondev.swiftech.core.presentation

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ralphmarondev.swiftech.core.domain.model.Result

@Composable
fun ResultDialog(
    result: Result?,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    modifier: Modifier = Modifier,
    dismissButtonText: String = "Dismiss",
    confirmButtonText: String = "Confirm",
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = onDismiss,
        dismissButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text(
                    text = dismissButtonText
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = onConfirm
            ) {
                Text(
                    text = confirmButtonText
                )
            }
        },
        text = {
            Text(
                text = result?.message ?: "Empty response message.",
                color = if (result?.success == false) {
                    MaterialTheme.colorScheme.error
                } else {
                    MaterialTheme.colorScheme.secondary
                }
            )
        },
        title = {
            Text(
                text = "Result",
                color = MaterialTheme.colorScheme.primary
            )
        }
    )
}