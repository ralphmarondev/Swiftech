package com.ralphmarondev.swiftech.di

import com.ralphmarondev.swiftech.core.di.coreModule
import com.ralphmarondev.swiftech.features.auth.di.authModule
import com.ralphmarondev.swiftech.features.courses.di.coursesModule
import com.ralphmarondev.swiftech.features.evaluation.di.evaluationModule
import com.ralphmarondev.swiftech.features.home.di.homeModule
import com.ralphmarondev.swiftech.features.students.di.studentModule
import com.ralphmarondev.swiftech.features.teachers.di.teacherModule

val appModule = listOf(
    coreModule,
    authModule,
    homeModule,
    studentModule,
    teacherModule,
    coursesModule,
    evaluationModule
)