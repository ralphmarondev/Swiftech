package com.ralphmarondev.swiftech.features.students.di

import com.ralphmarondev.swiftech.features.students.presentation.new_student.NewStudentViewModel
import com.ralphmarondev.swiftech.features.students.presentation.student_detail.StudentDetailViewModel
import com.ralphmarondev.swiftech.features.students.presentation.student_list.StudentListViewModel
import com.ralphmarondev.swiftech.features.students.presentation.update_student.UpdateStudentViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val studentModule = module {
    viewModelOf(::StudentListViewModel)
    viewModelOf(::NewStudentViewModel)
    viewModelOf(::StudentDetailViewModel)
    viewModelOf(::UpdateStudentViewModel)
}