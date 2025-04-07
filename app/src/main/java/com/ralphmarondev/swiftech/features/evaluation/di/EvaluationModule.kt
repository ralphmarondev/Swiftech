package com.ralphmarondev.swiftech.features.evaluation.di

import com.ralphmarondev.swiftech.features.evaluation.presentation.evaluation_list.EvaluationListViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val evaluationModule = module {
    viewModelOf(::EvaluationListViewModel)
}