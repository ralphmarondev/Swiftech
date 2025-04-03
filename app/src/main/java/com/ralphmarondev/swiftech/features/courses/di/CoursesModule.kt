package com.ralphmarondev.swiftech.features.courses.di

import com.ralphmarondev.swiftech.features.courses.presentation.course_list.CourseListViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val coursesModule = module {
    viewModelOf(::CourseListViewModel)
}