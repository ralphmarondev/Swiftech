package com.ralphmarondev.swiftech.settings.di

import com.ralphmarondev.swiftech.settings.presentation.SettingViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val settingsModule = module {
    viewModelOf(::SettingViewModel)
}