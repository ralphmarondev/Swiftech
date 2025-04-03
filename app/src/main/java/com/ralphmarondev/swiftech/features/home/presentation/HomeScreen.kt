package com.ralphmarondev.swiftech.features.home.presentation

import android.annotation.SuppressLint
import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material.icons.automirrored.outlined.MenuOpen
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.ralphmarondev.swiftech.R
import com.ralphmarondev.swiftech.core.util.LocalThemeState
import com.ralphmarondev.swiftech.features.home.domain.model.Options
import com.ralphmarondev.swiftech.features.home.presentation.components.AccountCard
import com.ralphmarondev.swiftech.features.home.presentation.components.OptionsCard
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@SuppressLint("ContextCastToActivity")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    username: String,
    onStudentClick: () -> Unit,
    onTeacherClick: () -> Unit,
    onCourseClick: () -> Unit,
    onEvaluationClick: () -> Unit,
    onLogout: () -> Unit
) {
    val themeState = LocalThemeState.current
    val viewModel: HomeViewModel = koinViewModel(parameters = { parametersOf(username) })
    val currentUser = viewModel.currentUser.collectAsState().value
    val showConfirmExitDiloag = viewModel.showConfirmExitDialog.collectAsState().value

    val options =
        when (currentUser?.role) {
            "Administrator" -> listOf(
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

            else -> {
                listOf(
                    Options(
                        name = "Courses",
                        image = R.drawable.students,
                        onClick = { onCourseClick() }
                    )
                )
            }
        }

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

@Composable
private fun DrawerContent(
    onLogout: () -> Unit
) {
    ModalDrawerSheet(
        modifier = Modifier
            .fillMaxHeight()
            .statusBarsPadding()
            .systemBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .defaultMinSize(minHeight = 200.dp)
                    .background(MaterialTheme.colorScheme.secondary)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Bottom
            ) {
                Image(
                    painter = rememberAsyncImagePainter(R.drawable.logo),
                    contentDescription = "logo",
                    modifier = Modifier
                        .size(120.dp),
                    contentScale = ContentScale.Crop
                )
                Text(
                    text = "Swiftech",
                    fontWeight = MaterialTheme.typography.titleMedium.fontWeight,
                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                    color = MaterialTheme.colorScheme.onSecondary,
                    modifier = Modifier.padding(8.dp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))

            NavigationDrawerItem(
                onClick = {},
                selected = false,
                label = {
                    Text(
                        text = "Home"
                    )
                },
                icon = {
                    Icon(
                        imageVector = Icons.Outlined.Home,
                        contentDescription = "Home"
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                shape = RoundedCornerShape(8.dp)
            )
            NavigationDrawerItem(
                onClick = {},
                selected = false,
                label = {
                    Text(
                        text = "Settings"
                    )
                },
                icon = {
                    Icon(
                        imageVector = Icons.Outlined.Settings,
                        contentDescription = "Settings"
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                shape = RoundedCornerShape(8.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
            NavigationDrawerItem(
                onClick = { onLogout() },
                selected = false,
                label = {
                    Text(
                        text = "Logout"
                    )
                },
                icon = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.Logout,
                        contentDescription = "Logout"
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                shape = RoundedCornerShape(8.dp)
            )
        }
    }
}

@Composable
fun ConfirmExitDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = onConfirm
            ) {
                Text(
                    text = "Confirm"
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
        },
        text = {
            Text(
                text = "Are you sure you want to exit the app?"
            )
        }
    )
}