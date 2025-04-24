package com.ralphmarondev.swiftech.admin_features.teachers.di

import com.ralphmarondev.swiftech.admin_features.teachers.presentation.new_teacher.NewTeacherViewModel
import com.ralphmarondev.swiftech.admin_features.teachers.presentation.teacher_detail.TeacherDetailViewModel
import com.ralphmarondev.swiftech.admin_features.teachers.presentation.teacher_list.TeacherListViewModel
import com.ralphmarondev.swiftech.admin_features.teachers.presentation.update_teacher.UpdateTeacherViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val teacherModule = module {
    viewModelOf(::TeacherListViewModel)
    viewModelOf(::NewTeacherViewModel)
    viewModelOf(::TeacherDetailViewModel)
    viewModelOf(::UpdateTeacherViewModel)
}