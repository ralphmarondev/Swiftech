package com.ralphmarondev.swiftech.features.home.presentation

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
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ralphmarondev.swiftech.R
import com.ralphmarondev.swiftech.core.util.LocalThemeState
import com.ralphmarondev.swiftech.features.home.domain.model.Options
import com.ralphmarondev.swiftech.features.home.presentation.components.AccountCard
import com.ralphmarondev.swiftech.features.home.presentation.components.OptionsCard
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

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
                    image = R.drawable.courses,
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

    BackHandler(enabled = true) {
        onLogout()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Swiftech"
                    )
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