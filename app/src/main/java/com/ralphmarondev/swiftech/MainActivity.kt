package com.ralphmarondev.swiftech

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.ralphmarondev.swiftech.core.util.ThemeProvider
import com.ralphmarondev.swiftech.core.util.ThemeState
import com.ralphmarondev.swiftech.navigation.AppNavigation
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {

    private val themeState: ThemeState by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ThemeProvider(themeState = themeState) {
                AppNavigation()
            }
        }
    }
}