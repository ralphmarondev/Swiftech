package com.ralphmarondev.swiftech.di

import com.ralphmarondev.swiftech.core.di.coreModule
import com.ralphmarondev.swiftech.features.auth.di.authModule
import com.ralphmarondev.swiftech.features.home.di.homeModule
import com.ralphmarondev.swiftech.features.students.di.studentModule

val appModule = listOf(
    coreModule,
    authModule,
    homeModule,
    studentModule
)