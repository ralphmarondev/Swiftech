package com.ralphmarondev.swiftech.features.home.presentation.admin

import android.annotation.SuppressLint
import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
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
import com.ralphmarondev.swiftech.R
import com.ralphmarondev.swiftech.core.util.LocalThemeState
import com.ralphmarondev.swiftech.features.home.domain.model.Options
import com.ralphmarondev.swiftech.features.home.presentation.components.AccountCard
import com.ralphmarondev.swiftech.features.home.presentation.components.ConfirmExitDialog
import com.ralphmarondev.swiftech.features.home.presentation.components.DrawerContent
import com.ralphmarondev.swiftech.features.home.presentation.components.OptionsCard
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@SuppressLint("ContextCastToActivity")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminHomeScreen(
    username: String,
    onStudentClick: () -> Unit,
    onTeacherClick: () -> Unit,
    onCourseClick: () -> Unit,
    onEvaluationClick: () -> Unit,
    onLogout: () -> Unit
) {
    val themeState = LocalThemeState.current
    val viewModel: AdminHomeViewModel = koinViewModel(parameters = { parametersOf(username) })
    val currentUser = viewModel.currentUser.collectAsState().value
    val showConfirmExitDiloag = viewModel.showConfirmExitDialog.collectAsState().value

    val options = listOf(
        Options(
            name = "Students",
            image = R.drawable.students,
            onClick = { onStudentClick() }
        ),
        Options(
            name = "Teachers",
            image = R.drawable.teachers,
            onClick = { onTeacherClick() }
        ),
        Options(
            name = "Courses",
            image = R.drawable.courses,
            onClick = { onCourseClick() }
        ),
        Options(
            name = "Evaluation",
            image = R.drawable.evaluation,
            onClick = { onEvaluationClick() }
        )
    )

    val activity = LocalContext.current as? Activity
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    BackHandler(enabled = true) {
        viewModel.setShowConfirmExitDialog()
    }

    ModalNavigationDrawer(
        drawerContent = {
            DrawerContent(onLogout)
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
                        .padding(16.dp)
                )

                Text(
                    text = "Manage",
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .align(Alignment.Start),
                    color = MaterialTheme.colorScheme.secondary
                )

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp)
                ) {
                    items(
                        count = options.size
                    ) { index ->
                        OptionsCard(
                            name = options[index].name,
                            image = options[index].image,
                            onClick = options[index].onClick
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

