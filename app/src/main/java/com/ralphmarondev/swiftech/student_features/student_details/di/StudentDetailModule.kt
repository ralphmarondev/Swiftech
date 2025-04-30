package com.ralphmarondev.swiftech.student_features.student_details.di

import com.ralphmarondev.swiftech.student_features.student_details.presentation.StudentDetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val studentDetailModule = module {
    viewModelOf(::StudentDetailViewModel)
}