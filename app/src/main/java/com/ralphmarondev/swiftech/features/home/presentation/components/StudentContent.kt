package com.ralphmarondev.swiftech.features.home.presentation.components

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ColumnScope.StudentContent() {
    Text(
        text = "Courses",
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .align(Alignment.Start),
        color = MaterialTheme.colorScheme.secondary
    )
}