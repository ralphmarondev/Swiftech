package com.ralphmarondev.swiftech.admin_features.reports.di

import com.ralphmarondev.swiftech.admin_features.reports.data.repository.ReportRepositoryImpl
import com.ralphmarondev.swiftech.admin_features.reports.domain.repository.ReportRepository
import com.ralphmarondev.swiftech.admin_features.reports.domain.usecase.ComputeAverageRatingUseCase
import com.ralphmarondev.swiftech.admin_features.reports.domain.usecase.ComputeRatingCountsUseCase
import com.ralphmarondev.swiftech.admin_features.reports.domain.usecase.GetQuestionRatingReportsByCourseUseCase
import com.ralphmarondev.swiftech.admin_features.reports.presentation.ReportViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val reportModule = module {
    single<ReportRepository> { ReportRepositoryImpl(get()) }

    factoryOf(::ComputeAverageRatingUseCase)
    factoryOf(::ComputeRatingCountsUseCase)
    factoryOf(::GetQuestionRatingReportsByCourseUseCase)

    viewModelOf(::ReportViewModel)
}