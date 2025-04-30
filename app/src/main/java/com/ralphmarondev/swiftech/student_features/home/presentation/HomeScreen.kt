package com.ralphmarondev.swiftech.student_features.home.presentation

import android.annotation.SuppressLint
import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.MenuOpen
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.ralphmarondev.swiftech.core.presentation.AccountCard
import com.ralphmarondev.swiftech.core.presentation.ConfirmExitDialog
import com.ralphmarondev.swiftech.core.presentation.CourseCard
import com.ralphmarondev.swiftech.core.presentation.DrawerContent
import com.ralphmarondev.swiftech.core.util.LocalThemeState
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@SuppressLint("ContextCastToActivity")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    username: String,
    onCourseClick: (Int) -> Unit,
    onAccountCardClick: (String) -> Unit,
    onLogout: () -> Unit
) {
    val themeState = LocalThemeState.current
    val viewModel: HomeViewModel = koinViewModel(parameters = { parametersOf(username) })
    val currentUser = viewModel.currentUser.collectAsState().value
    val showConfirmExitDiloag = viewModel.showConfirmExitDialog.collectAsState().value
    val courses = viewModel.courses.collectAsState().value

    val activity = LocalContext.current as? Activity
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    BackHandler(enabled = true) {
        viewModel.setShowConfirmExitDialog()
    }

    ModalNavigationDrawer(
        drawerContent = {
            DrawerContent(
                onLogout = onLogout,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 24.dp)
            )
        },
        drawerState = drawerState
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "Swiftech"
                        )
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                scope.launch {
                                    drawerState.open()
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Outlined.MenuOpen,
                                contentDescription = "Open menu"
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = themeState::toggleTheme) {
                            val imageVector = if (themeState.darkTheme.value) {
                                Icons.Outlined.LightMode
                            } else {
                                Icons.Outlined.DarkMode
                            }
                            Icon(
                                imageVector = imageVector,
                                contentDescription = "Theme toggle"
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
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AccountCard(
                    name = currentUser?.fullName ?: "No Name",
                    role = currentUser?.role ?: "No role",
                    image = currentUser?.image ?: "No image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    onClick = { onAccountCardClick(username) }
                )

                Text(
                    text = "Courses",
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .align(Alignment.Start),
                    color = MaterialTheme.colorScheme.secondary
                )
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(courses) {
                        CourseCard(
                            onClick = {
                                onCourseClick(it.id)
                            },
                            course = it,
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                    }
                }
            }
        }
    }

    if (showConfirmExitDiloag) {
        ConfirmExitDialog(
            onDismiss = {
                viewModel.setShowConfirmExitDialog()
            },
            onConfirm = {
                activity?.finish()
            }
        )
    }
}