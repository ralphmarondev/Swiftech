package com.ralphmarondev.swiftech.admin_features.reports.di

import com.ralphmarondev.swiftech.admin_features.reports.presentation.ReportViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val reportModule = module {
    viewModelOf(::ReportViewModel)
}