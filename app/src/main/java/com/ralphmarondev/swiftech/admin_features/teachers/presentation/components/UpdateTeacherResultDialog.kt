package com.ralphmarondev.swiftech.admin_features.teachers.presentation.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ralphmarondev.swiftech.core.domain.model.Result

@Composable
fun UpdateTeacherResultDialog(
    result: Result?,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text(
                    text = "Close"
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