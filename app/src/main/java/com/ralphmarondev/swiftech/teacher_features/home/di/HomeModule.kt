package com.ralphmarondev.swiftech.teacher_features.home.di

import com.ralphmarondev.swiftech.teacher_features.home.data.repository.HomeRepositoryImpl
import com.ralphmarondev.swiftech.teacher_features.home.domain.repository.HomeRepository
import com.ralphmarondev.swiftech.teacher_features.home.domain.usecase.GetCoursesByTeacherIdUseCase
import com.ralphmarondev.swiftech.teacher_features.home.presentation.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val homeModule = module {
    single<HomeRepository> { HomeRepositoryImpl(get()) }

    factoryOf(::GetCoursesByTeacherIdUseCase)

    viewModelOf(::HomeViewModel)
}