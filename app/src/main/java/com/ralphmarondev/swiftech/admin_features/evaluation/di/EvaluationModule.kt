package com.ralphmarondev.swiftech.admin_features.evaluation.di

import com.ralphmarondev.swiftech.admin_features.evaluation.presentation.evaluation_detail.EvaluationDetailViewModel
import com.ralphmarondev.swiftech.admin_features.evaluation.presentation.evaluation_list.EvaluationListViewModel
import com.ralphmarondev.swiftech.admin_features.evaluation.presentation.new_evaluation.NewEvaluationViewModel
import com.ralphmarondev.swiftech.admin_features.evaluation.presentation.update_evaluation.UpdateEvaluationViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val evaluationModule = module {
    viewModelOf(::EvaluationListViewModel)
    viewModelOf(::NewEvaluationViewModel)
    viewModelOf(::EvaluationDetailViewModel)
    viewModelOf(::UpdateEvaluationViewModel)
}