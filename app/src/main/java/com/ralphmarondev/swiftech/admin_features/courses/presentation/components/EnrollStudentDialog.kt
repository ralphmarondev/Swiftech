package com.ralphmarondev.swiftech.admin_features.courses.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ralphmarondev.swiftech.core.presentation.NormalTextField
import com.ralphmarondev.swiftech.admin_features.courses.presentation.course_detail.CourseDetailViewModel

@Composable
fun EnrollStudentDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    viewModel: CourseDetailViewModel
) {
    val username = viewModel.studentToEnroll.collectAsState().value
    val response = viewModel.response.collectAsState().value

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Enroll Student",
                color = MaterialTheme.colorScheme.primary
            )
        },
        text = {
            Column {
                NormalTextField(
                    value = username,
                    onValueChange = viewModel::onUsernameValueChange,
                    label = "Username",
                    leadingIcon = Icons.Outlined.AccountBox,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )

                Spacer(modifier = Modifier.height(2.dp))
                AnimatedVisibility(response?.success == false) {
                    if (response?.message != null) {
                        Text(
                            text = response.message,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.W500,
                            color = if (response.success) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.error,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp, vertical = 16.dp)
                        )
                    }
                }
                AnimatedVisibility(response?.success == true) {
                    if (response?.message != null) {
                        Text(
                            text = response.message,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.W500,
                            color = if (response.success) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.error,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp, vertical = 16.dp)
                        )
                    }
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = onConfirm
            ) {
                Text(
                    text = "Enroll"
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text(
                    text = "Cancel"
                )
            }
        }
    )
}