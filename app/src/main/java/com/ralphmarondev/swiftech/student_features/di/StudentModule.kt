package com.ralphmarondev.swiftech.student_features.di

import com.ralphmarondev.swiftech.student_features.evaluate.di.evaluateModule
import com.ralphmarondev.swiftech.student_features.home.di.homeModule

val studentFeatureModule = listOf(
    homeModule,
    evaluateModule
)