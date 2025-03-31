package com.ralphmarondev.swiftech.core.util

import android.content.Context
import android.net.Uri
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

fun saveDrawableToInternalStorage(
    context: Context,
    drawableRes: Int,
    fileName: String
): String {
    val file = File(context.filesDir, fileName)

    if (!file.exists()) {
        val inputStream = context.resources.openRawResource(drawableRes)
        val outputStream = FileOutputStream(file)

        inputStream.use { input ->
            outputStream.use { output ->
                input.copyTo(output)
            }
        }
    }

    return file.absolutePath
}

fun saveImageToAppFolder(context: Context, imageUri: Uri): String? {
    return try {
        val fileName = "IMG_${System.currentTimeMillis()}.png"
        val file = File(context.filesDir, fileName)

        val inputStream: InputStream? = context.contentResolver.openInputStream(imageUri)
        FileOutputStream(file).use { outputStream ->
            inputStream?.copyTo(outputStream)
        }
        file.absolutePath
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}