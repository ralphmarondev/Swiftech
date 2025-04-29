package com.ralphmarondev.swiftech.teacher_features.home.di

import com.ralphmarondev.swiftech.teacher_features.home.presentation.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val homeModule = module {
    viewModelOf(::HomeViewModel)
}