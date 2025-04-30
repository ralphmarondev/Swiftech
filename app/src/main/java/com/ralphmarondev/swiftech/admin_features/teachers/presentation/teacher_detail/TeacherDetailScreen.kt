package com.ralphmarondev.swiftech.admin_features.teachers.presentation.teacher_detail

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.ralphmarondev.swiftech.admin_features.teachers.presentation.components.DeleteTeacherDialog
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeacherDetailScreen(
    navigateBack: () -> Unit,
    username: String,
    updateTeacher: () -> Unit
) {
    val viewModel: TeacherDetailViewModel = koinViewModel(parameters = { parametersOf(username) })
    val teacherDetail = viewModel.userDetail.collectAsState().value
    val showDeleteDialog = viewModel.showDeleteDialog.collectAsState().value

    LaunchedEffect(username) {
        viewModel.refreshDetails()
        Log.d("App", "Refreshing teacher details screen data...")
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Teacher Details"
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    if (teacherDetail?.image.isNullOrBlank()) {
                        val initials = teacherDetail?.fullName?.let { name ->
                            val parts = name.split(" ")
                                .filter { it.isNotBlank() }

                            when {
                                parts.size >= 2 -> "${
                                    parts[0].first().uppercaseChar()
                                }${parts[1].first().uppercaseChar()}"

                                parts.size == 1 -> parts[0].first().uppercaseChar().toString()
                                else -> "?"
                            }
                        } ?: "?"
                        val fontSize = if (initials.length == 1) 28.sp else 20.sp

                        Box(
                            modifier = Modifier
                                .size(100.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.primary),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = initials,
                                color = MaterialTheme.colorScheme.onPrimary,
                                fontSize = fontSize,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center
                            )
                        }
                    } else {
                        Image(
                            painter = rememberAsyncImagePainter(teacherDetail?.image),
                            contentDescription = teacherDetail?.username,
                            modifier = Modifier
                                .size(100.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    }

                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = teacherDetail?.fullName ?: "No full name provided.",
                            fontSize = 18.sp,
                            fontWeight = MaterialTheme.typography.titleLarge.fontWeight,
                            color = MaterialTheme.colorScheme.primary,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = teacherDetail?.username ?: "No username provided.",
                            fontSize = 16.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            fontWeight = MaterialTheme.typography.titleMedium.fontWeight,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(
                    onClick = updateTeacher,
                    modifier = Modifier
                        .weight(1f)
                ) {
                    Text(
                        text = "UPDATE",
                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
                        fontWeight = MaterialTheme.typography.titleMedium.fontWeight
                    )
                }

                ElevatedButton(
                    onClick = viewModel::setDeleteDialog,
                    modifier = Modifier
                        .weight(1f)
                ) {
                    Text(
                        text = "DELETE",
                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
                        fontWeight = MaterialTheme.typography.titleMedium.fontWeight
                    )
                }
            }
        }
    }

    if (showDeleteDialog) {
        DeleteTeacherDialog(
            username = teacherDetail?.fullName ?: teacherDetail?.username
            ?: "No username provided.",
            onConfirm = {
                viewModel.deleteUser()
                viewModel.setDeleteDialog()
                navigateBack()
            },
            onDismiss = viewModel::setDeleteDialog
        )
    }
}