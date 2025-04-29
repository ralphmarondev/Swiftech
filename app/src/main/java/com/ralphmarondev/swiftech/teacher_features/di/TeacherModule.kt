package com.ralphmarondev.swiftech.teacher_features.di

import com.ralphmarondev.swiftech.teacher_features.home.di.homeModule
import com.ralphmarondev.swiftech.teacher_features.reports.di.reportModule

val teacherFeatureModule = listOf(
    homeModule,
    reportModule
)