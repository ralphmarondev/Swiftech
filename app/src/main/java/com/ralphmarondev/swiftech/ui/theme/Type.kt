package com.ralphmarondev.swiftech.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.ralphmarondev.swiftech.R

// Define different font families for different weights
val robotoRegular = FontFamily(
    Font(R.font.roboto_regular)
)

val robotoBold = FontFamily(
    Font(R.font.roboto_bold)
)

val robotoThin = FontFamily(
    Font(R.font.roboto_thin)
)

// Default Material 3 typography values
val baseline = Typography()

val Typography = Typography(
    displayLarge = baseline.displayLarge.copy(fontFamily = robotoBold),
    displayMedium = baseline.displayMedium.copy(fontFamily = robotoBold),
    displaySmall = baseline.displaySmall.copy(fontFamily = robotoBold),
    headlineLarge = baseline.headlineLarge.copy(fontFamily = robotoBold),
    headlineMedium = baseline.headlineMedium.copy(fontFamily = robotoBold),
    headlineSmall = baseline.headlineSmall.copy(fontFamily = robotoBold),
    titleLarge = baseline.titleLarge.copy(fontFamily = robotoRegular),
    titleMedium = baseline.titleMedium.copy(fontFamily = robotoRegular),
    titleSmall = baseline.titleSmall.copy(fontFamily = robotoRegular),
    bodyLarge = baseline.bodyLarge.copy(fontFamily = robotoRegular),
    bodyMedium = baseline.bodyMedium.copy(fontFamily = robotoRegular),
    bodySmall = baseline.bodySmall.copy(fontFamily = robotoRegular),
    labelLarge = baseline.labelLarge.copy(fontFamily = robotoRegular),
    labelMedium = baseline.labelMedium.copy(fontFamily = robotoRegular),
    labelSmall = baseline.labelSmall.copy(fontFamily = robotoRegular),
)