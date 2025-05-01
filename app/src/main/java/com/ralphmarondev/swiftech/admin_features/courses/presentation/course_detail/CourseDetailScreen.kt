package com.ralphmarondev.swiftech.admin_features.courses.presentation.course_detail

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material.icons.outlined.DatasetLinked
import androidx.compose.material.icons.outlined.DeleteOutline
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
import com.ralphmarondev.swiftech.admin_features.courses.presentation.components.CourseResultDialog
import com.ralphmarondev.swiftech.admin_features.courses.presentation.components.DeleteCourseDialog
import com.ralphmarondev.swiftech.admin_features.courses.presentation.components.EnrollStudentDialog
import com.ralphmarondev.swiftech.admin_features.courses.presentation.components.RemoveStudentDialog
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CourseDetailScreen(
    courseId: Int,
    navigateBack: () -> Unit,
    updateCourse: (Int) -> Unit,
    navigateToReports: (Int) -> Unit
) {
    val viewModel: CourseDetailViewModel = koinViewModel(parameters = { parametersOf(courseId) })
    val courseDetail = viewModel.courseDetail.collectAsState().value
    val teacherDetail = viewModel.teacherDetail.collectAsState().value
    val students = viewModel.students.collectAsState().value
    val showEnrollStudentDialog = viewModel.showEnrollStudentDialog.collectAsState().value
    val showRemoveStudentDialog = viewModel.removeStudentDialog.collectAsState().value
    val selectedStudent = viewModel.selectedStudent.collectAsState().value
    val removeStudentResponse = viewModel.removeStudentResponse.collectAsState().value
    val showDeleteCourseDialog = viewModel.showDeleteCourseDialog.collectAsState().value
    val showResultDialog = viewModel.showResultDialog.collectAsState().value
    val deleteResponse = viewModel.deleteResponse.collectAsState().value

    LaunchedEffect(Unit) {
        Log.d("App", "Refreshing course details...")
        viewModel.refreshCourse()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Course Details"
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
                        onClick = { viewModel.setShowDeleteCourseDialog(true) }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.DeleteOutline,
                            contentDescription = "Delete"
                        )
                    }
                    IconButton(onClick = { navigateToReports(courseId) }) {
                        Icon(
                            imageVector = Icons.Outlined.DatasetLinked,
                            contentDescription = "Reports"
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
                onClick = viewModel::setShowEnrollStudentDialog
            ) {
                Icon(
                    imageVector = Icons.Outlined.Add,
                    contentDescription = "New student"
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
            item { Spacer(modifier = Modifier.height(8.dp)) }
            item {
                ElevatedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    onClick = {
                        updateCourse(courseId)
                    }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        if (courseDetail?.image.isNullOrBlank()) {
                            val initials = courseDetail?.name?.let { name ->
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
                                    .background(MaterialTheme.colorScheme.secondary),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = initials,
                                    color = MaterialTheme.colorScheme.onSecondary,
                                    fontSize = fontSize,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center
                                )
                            }
                        } else {
                            Image(
                                painter = rememberAsyncImagePainter(courseDetail?.image),
                                contentDescription = courseDetail?.name,
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
                                text = courseDetail?.name ?: "No course name provided.",
                                fontSize = 18.sp,
                                fontWeight = MaterialTheme.typography.titleLarge.fontWeight,
                                color = MaterialTheme.colorScheme.primary,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )
                            Text(
                                text = courseDetail?.code ?: "No course code provided.",
                                fontSize = 16.sp,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                fontWeight = MaterialTheme.typography.titleMedium.fontWeight,
                                color = MaterialTheme.colorScheme.secondary
                            )
                        }
                    }
                }

                Text(
                    text = "Teacher:",
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(start = 6.dp)
                )
                ElevatedCard(
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
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
                                    .size(60.dp)
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.secondary),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = initials,
                                    color = MaterialTheme.colorScheme.onSecondary,
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
                                    .size(60.dp)
                                    .clip(CircleShape),
                                contentScale = ContentScale.Crop
                            )
                        }

                        Text(
                            text = teacherDetail?.fullName ?: "No full name provided.",
                            fontSize = MaterialTheme.typography.titleMedium.fontSize,
                            fontWeight = MaterialTheme.typography.titleMedium.fontWeight,
                            color = MaterialTheme.colorScheme.primary,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }

                Text(
                    text = "Students:",
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(start = 6.dp, top = 8.dp)
                )
            }
            item {
                AnimatedVisibility(students.isEmpty()) {
                    Text(
                        text = "No students yet.",
                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
                        fontWeight = MaterialTheme.typography.titleMedium.fontWeight,
                        color = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.padding(start = 6.dp)
                    )
                }
            }
            items(students) { student ->
                ElevatedCard(
                    modifier = Modifier
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    onClick = {
                        viewModel.onStudentClick(student)
                        viewModel.setRemoveStudentDialog(true)
                    }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        if (student.image.isNullOrBlank()) {
                            val initials = student.fullName.let { name ->
                                val parts = name.split(" ")
                                    .filter { it.isNotBlank() }

                                when {
                                    parts.size >= 2 -> "${
                                        parts[0].first().uppercaseChar()
                                    }${parts[1].first().uppercaseChar()}"

                                    parts.size == 1 -> parts[0].first().uppercaseChar().toString()
                                    else -> "?"
                                }
                            }
                            val fontSize = if (initials.length == 1) 28.sp else 20.sp

                            Box(
                                modifier = Modifier
                                    .size(60.dp)
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.secondary),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = initials,
                                    color = MaterialTheme.colorScheme.onSecondary,
                                    fontSize = fontSize,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center
                                )
                            }
                        } else {
                            Image(
                                painter = rememberAsyncImagePainter(student.image),
                                contentDescription = student.username,
                                modifier = Modifier
                                    .size(60.dp)
                                    .clip(CircleShape),
                                contentScale = ContentScale.Crop
                            )
                        }

                        Text(
                            text = student.fullName,
                            fontSize = MaterialTheme.typography.titleMedium.fontSize,
                            fontWeight = MaterialTheme.typography.titleMedium.fontWeight,
                            color = MaterialTheme.colorScheme.primary,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
            item { Spacer(modifier = Modifier.height(100.dp)) }
        }
    }

    if (showEnrollStudentDialog) {
        EnrollStudentDialog(
            onDismiss = viewModel::setShowEnrollStudentDialog,
            onConfirm = {
                viewModel.enrollStudent()
            },
            viewModel = viewModel
        )
    }

    if (showRemoveStudentDialog) {
        RemoveStudentDialog(
            text = "Are you sure you want to remove ${selectedStudent?.fullName} from the class?",
            onDismiss = {
                viewModel.setRemoveStudentDialog(false)
            },
            onConfirm = {
                viewModel.removeStudentInClass()
                if (removeStudentResponse?.success == true) {
                    viewModel.setRemoveStudentDialog(false)
                }
            }
        )
    }
    if (showDeleteCourseDialog) {
        DeleteCourseDialog(
            text = "Are you sure you want to delete this course and all associated data like evaluations reports?",
            onDismiss = {
                viewModel.setShowDeleteCourseDialog(false)
            },
            onConfirm = {
                viewModel.deleteCourse()
            }
        )
    }
    if (showResultDialog) {
        CourseResultDialog(
            onDismiss = {
                viewModel.setShowResultDialog(false)
            },
            onConfirm = {
                viewModel.setShowResultDialog(false)
                if (deleteResponse?.success == true) {
                    navigateBack()
                }
            },
            result = deleteResponse
        )
    }
}