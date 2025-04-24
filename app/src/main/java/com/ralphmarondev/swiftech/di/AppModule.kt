package com.ralphmarondev.swiftech.di

import com.ralphmarondev.swiftech.core.di.coreModule
import com.ralphmarondev.swiftech.auth.di.authModule
import com.ralphmarondev.swiftech.admin_features.courses.di.coursesModule
import com.ralphmarondev.swiftech.admin_features.evaluation.di.evaluationModule
import com.ralphmarondev.swiftech.admin_features.home.di.homeModule
import com.ralphmarondev.swiftech.admin_features.students.di.studentModule
import com.ralphmarondev.swiftech.admin_features.teachers.di.teacherModule

val appModule = listOf(
    coreModule,
    authModule,
    homeModule,
    studentModule,
    teacherModule,
    coursesModule,
    evaluationModule
)