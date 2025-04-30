package com.ralphmarondev.swiftech.core.presentation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.SendTimeExtension
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.ralphmarondev.swiftech.core.domain.model.Gender

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenderDropdown(
    gender: String,
    onGenderChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    singleLine: Boolean = true
) {
    val focusManager = LocalFocusManager.current
    val genderOptions = listOf(Gender.MALE, Gender.FEMALE)
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
            focusManager.clearFocus()
        },
        modifier = modifier
    ) {
        OutlinedTextField(
            value = gender,
            onValueChange = {},
            readOnly = true,
            label = { Text(text = "Gender") },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded)
            },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
            textStyle = TextStyle(
                fontWeight = FontWeight.W500,
                fontSize = 16.sp,
                fontFamily = MaterialTheme.typography.bodyLarge.fontFamily,
                color = MaterialTheme.colorScheme.secondary
            ),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.SendTimeExtension,
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.secondary
                )
            },
            singleLine = singleLine,
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
                focusManager.clearFocus()
            }
        ) {
            genderOptions.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        onGenderChange(option)
                        expanded = false
                        focusManager.clearFocus()
                    }
                )
            }
        }
    }
}
