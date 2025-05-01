package com.ralphmarondev.swiftech.admin_features.courses.presentation.update_course

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Grading
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material.icons.outlined.QrCode
import androidx.compose.material.icons.outlined.Timeline
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.ralphmarondev.swiftech.admin_features.courses.presentation.components.CourseResultDialog
import com.ralphmarondev.swiftech.core.presentation.NormalTextField
import com.ralphmarondev.swiftech.core.util.saveImageToAppFolder
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateCourseScreen(
    courseId: Int,
    navigateBack: () -> Unit
) {
    val viewModel: UpdateCourseViewModel = koinViewModel(parameters = { parametersOf(courseId) })
    val name = viewModel.name.collectAsState().value
    val code = viewModel.code.collectAsState().value
    val term = viewModel.term.collectAsState().value
    val teacher = viewModel.teacher.collectAsState().value
    val image = viewModel.image.collectAsState().value
    val response = viewModel.response.collectAsState().value
    val showResultDialog = viewModel.showResultDialog.collectAsState().value

    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            val path = saveImageToAppFolder(context, it)
            path?.let { image ->
                viewModel.setImageValue(image)
            }
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Update"
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
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            if (image.isBlank()) {
                val initials = name
                    .split(" ")
                    .filter { it.isNotBlank() }
                    .take(2)
                    .map { it.first().uppercaseChar() }
                    .joinToString("")
                val fontSize = if (initials.length == 1) 38.sp else 30.sp
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
                    painter = rememberAsyncImagePainter(image),
                    contentDescription = name,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            TextButton(
                onClick = {
                    launcher.launch("image/*")
                }
            ) {
                Text(
                    text = "Change photo",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.outline
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
            NormalTextField(
                value = name,
                onValueChange = viewModel::onNameValueChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                label = "Name",
                placeholder = "Technopreneurship",
                leadingIcon = Icons.AutoMirrored.Outlined.Grading,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Next)
                    }
                )
            )

            NormalTextField(
                value = code,
                onValueChange = viewModel::onCodeValueChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                label = "Code",
                placeholder = "T2025",
                leadingIcon = Icons.Outlined.QrCode,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Next)
                    }
                )
            )

            NormalTextField(
                value = term,
                onValueChange = viewModel::onTermValueChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                label = "Term",
                placeholder = "24-25-1",
                leadingIcon = Icons.Outlined.Timeline,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Next)
                    }
                )
            )

            NormalTextField(
                value = teacher,
                onValueChange = viewModel::onTeacherValueChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                label = "Teacher",
                leadingIcon = Icons.Outlined.AccountBox,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                    }
                )
            )

            Spacer(modifier = Modifier.height(2.dp))
            AnimatedVisibility(visible = response?.success == false) {
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
            AnimatedVisibility(visible = response?.success == true) {
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
            Spacer(modifier = Modifier.height(4.dp))

            Button(
                onClick = viewModel::updateCourse,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(
                    text = "UPDATE",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.W500,
                    fontSize = 16.sp
                )
            }
        }
    }

    if (showResultDialog) {
        CourseResultDialog(
            result = response,
            onDismiss = {
                viewModel.setShowResultDialog(false)
            },
            onConfirm = {
                viewModel.setShowResultDialog(false)

                if (response?.success == true) {
                    navigateBack()
                }
            }
        )
    }
}