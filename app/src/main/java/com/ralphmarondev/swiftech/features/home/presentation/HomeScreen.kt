package com.ralphmarondev.swiftech.features.home.presentation

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.ralphmarondev.swiftech.core.util.LocalThemeState
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    val themeState = LocalThemeState.current
    val viewModel: HomeViewModel = koinViewModel()
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current
    var extractedText by remember { mutableStateOf("") }

    // Image capture activity result
    val imageCaptureLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { isSuccess ->
            if (isSuccess && imageUri != null) {
                // Pass the image URI to the ViewModel for text recognition
                imageUri?.let {
                    viewModel.recognizeTextFromImage(
                        it,
                        context,
                        onSuccess = { text ->
                            extractedText = text
                        },
                        onFailure = { exception ->
                            extractedText = "Error: ${exception.message}"
                        }
                    )
                }
            }
        }
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Swiftech")
                },
                actions = {
                    IconButton(onClick = themeState::toggleTheme) {
                        val imageVector = if (themeState.darkTheme.value) {
                            Icons.Outlined.LightMode
                        } else {
                            Icons.Outlined.DarkMode
                        }
                        Icon(imageVector = imageVector, contentDescription = "Theme toggle")
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
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                onClick = {
                    // Prepare image capture
                    val uri =
                        createImageUri(context) // You can replace this with your URI creation method
                    imageUri = uri
                    imageCaptureLauncher.launch(uri)
                }
            ) {
                Text(
                    text = "Capture image",
                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                    fontWeight = MaterialTheme.typography.titleMedium.fontWeight,
                    modifier = Modifier.padding(4.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            imageUri?.let {
                Text("Captured Image URI: $it")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text("Extracted Text: $extractedText")
        }
    }
}

// Create an image URI for capturing
private fun createImageUri(context: Context): Uri {
    val contentResolver = context.contentResolver
    val values = ContentValues().apply {
        put(MediaStore.Images.Media.TITLE, "CapturedImage")
        put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis() / 1000)
    }
    return contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)!!
}
