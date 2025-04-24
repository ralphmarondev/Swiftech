package com.ralphmarondev.swiftech.student_features.evaluate.di

import com.ralphmarondev.swiftech.student_features.evaluate.presentation.EvaluateViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val evaluateModule = module {
    viewModelOf(::EvaluateViewModel)
}