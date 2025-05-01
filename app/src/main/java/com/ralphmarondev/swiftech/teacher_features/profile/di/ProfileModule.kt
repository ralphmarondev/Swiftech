package com.ralphmarondev.swiftech.teacher_features.profile.di

import com.ralphmarondev.swiftech.teacher_features.profile.presentation.ProfileViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val profileModule = module {
    viewModelOf(::ProfileViewModel)
}