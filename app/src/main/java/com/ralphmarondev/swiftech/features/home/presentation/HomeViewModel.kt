package com.ralphmarondev.swiftech.features.home.presentation

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    // Function to recognize text from the image
    fun recognizeTextFromImage(
        uri: Uri,
        context: Context,
        onSuccess: (String) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        try {
            val inputImage = InputImage.fromFilePath(context, uri)

            val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

            viewModelScope.launch {
                recognizer.process(inputImage)
                    .addOnSuccessListener { visionText ->
                        onSuccess(visionText.text)
                    }
                    .addOnFailureListener { exception ->
                        onFailure(exception)
                    }
            }
        } catch (e: Exception) {
            onFailure(e)
        }
    }
}

