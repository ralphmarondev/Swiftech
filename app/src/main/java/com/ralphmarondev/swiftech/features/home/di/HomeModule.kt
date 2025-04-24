package com.ralphmarondev.swiftech.features.home.di

import com.ralphmarondev.swiftech.features.home.navigation.HomeNavigationViewModel
import com.ralphmarondev.swiftech.features.home.presentation.admin.AdminHomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val homeModule = module {
    viewModelOf(::HomeNavigationViewModel)
    viewModelOf(::AdminHomeViewModel)
}