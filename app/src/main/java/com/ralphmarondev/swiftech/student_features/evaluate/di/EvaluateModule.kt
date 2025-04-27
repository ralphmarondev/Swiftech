package com.ralphmarondev.swiftech.student_features.evaluate.di

import com.ralphmarondev.swiftech.student_features.evaluate.data.repository.EvaluateRepositoryImpl
import com.ralphmarondev.swiftech.student_features.evaluate.domain.repository.EvaluateRepository
import com.ralphmarondev.swiftech.student_features.evaluate.domain.usecase.GetEvaluationFormDetailByIdUseCase
import com.ralphmarondev.swiftech.student_features.evaluate.domain.usecase.GetEvaluationFormQuestionByIdUseCase
import com.ralphmarondev.swiftech.student_features.evaluate.domain.usecase.SubmitEvaluationUseCase
import com.ralphmarondev.swiftech.student_features.evaluate.presentation.EvaluateViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val evaluateModule = module {
    viewModelOf(::EvaluateViewModel)

    single<EvaluateRepository> { EvaluateRepositoryImpl(get()) }

    factoryOf(::GetEvaluationFormQuestionByIdUseCase)
    factoryOf(::GetEvaluationFormDetailByIdUseCase)
    factoryOf(::SubmitEvaluationUseCase)
}