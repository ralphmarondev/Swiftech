package com.ralphmarondev.swiftech.settings.presentation.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun DeleteSavedCredentials(
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
                    text = "Delete"
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
                text = "Are you sure you want to remove saved username and password?",
                color = MaterialTheme.colorScheme.secondary
            )
        },
        title = {
            Text(
                text = "Delete Saved Credentials",
                color = MaterialTheme.colorScheme.primary
            )
        }
    )
}