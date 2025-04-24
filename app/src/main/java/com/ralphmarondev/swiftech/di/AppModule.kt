package com.ralphmarondev.swiftech.di

import com.ralphmarondev.swiftech.admin_features.di.adminFeatureModule
import com.ralphmarondev.swiftech.auth.di.authModule
import com.ralphmarondev.swiftech.core.di.coreModule
import com.ralphmarondev.swiftech.student_features.di.studentFeatureModule

val appModule = buildList {
    addAll(
        listOf(
            coreModule,
            authModule
        )
    )
    addAll(adminFeatureModule)
    addAll(studentFeatureModule)
}
