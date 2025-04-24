package com.ralphmarondev.swiftech.features.home.navigation

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.ralphmarondev.swiftech.core.domain.model.Role
import com.ralphmarondev.swiftech.features.home.presentation.admin.AdminHomeScreen
import com.ralphmarondev.swiftech.features.home.presentation.student.StudentHomeScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeNavigation(
    username: String,
    onStudentClick: () -> Unit,
    onTeacherClick: () -> Unit,
    onCourseClick: () -> Unit,
    onEvaluationClick: () -> Unit,
    onLogout: () -> Unit
) {
    val viewModel: HomeNavigationViewModel = koinViewModel()
    val currentUser = viewModel.currentUser.collectAsState().value

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when (currentUser?.role) {
            Role.ADMINISTRATOR -> {
                AdminHomeScreen(
                    username = username,
                    onStudentClick = onStudentClick,
                    onTeacherClick = onTeacherClick,
                    onCourseClick = onCourseClick,
                    onEvaluationClick = onEvaluationClick,
                    onLogout = onLogout
                )
            }

            Role.STUDENT -> {
                StudentHomeScreen(
                    username = username,
                    onLogout = onLogout,
                    onCourseClick = { id ->
                        Log.d("App", "Course id: `$id`")
                    }
                )
            }

            Role.TEACHER -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Coming soon..."
                    )
                }
            }

            else -> {
                Text(
                    text = "Invalid role."
                )
            }
        }
    }
}