package com.ralphmarondev.swiftech.core.util

import android.content.Context
import java.io.File
import java.io.FileOutputStream

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