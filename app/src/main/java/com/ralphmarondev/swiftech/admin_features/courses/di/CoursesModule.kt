package com.ralphmarondev.swiftech.admin_features.courses.di

import com.ralphmarondev.swiftech.admin_features.courses.presentation.course_detail.CourseDetailViewModel
import com.ralphmarondev.swiftech.admin_features.courses.presentation.course_list.CourseListViewModel
import com.ralphmarondev.swiftech.admin_features.courses.presentation.new_course.NewCourseViewModel
import com.ralphmarondev.swiftech.admin_features.courses.presentation.update_course.UpdateCourseViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val coursesModule = module {
    viewModelOf(::CourseListViewModel)
    viewModelOf(::NewCourseViewModel)
    viewModelOf(::CourseDetailViewModel)
    viewModelOf(::UpdateCourseViewModel)
}