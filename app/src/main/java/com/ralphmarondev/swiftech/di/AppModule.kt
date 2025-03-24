package com.ralphmarondev.swiftech.di

import com.ralphmarondev.swiftech.core.di.coreModule
import com.ralphmarondev.swiftech.features.home.di.homeModule

val appModule = listOf(
    coreModule,
    homeModule
)