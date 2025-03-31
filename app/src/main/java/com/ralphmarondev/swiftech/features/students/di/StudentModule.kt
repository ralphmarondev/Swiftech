package com.ralphmarondev.swiftech.features.students.di

import com.ralphmarondev.swiftech.features.students.presentation.student_list.StudentListViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val studentModule = module {
    viewModelOf(::StudentListViewModel)
}