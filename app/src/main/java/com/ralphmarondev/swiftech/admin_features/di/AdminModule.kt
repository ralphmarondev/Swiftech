package com.ralphmarondev.swiftech.admin_features.di

import com.ralphmarondev.swiftech.admin_features.courses.di.coursesModule
import com.ralphmarondev.swiftech.admin_features.evaluation.di.evaluationModule
import com.ralphmarondev.swiftech.admin_features.home.di.homeModule
import com.ralphmarondev.swiftech.admin_features.reports.di.reportModule
import com.ralphmarondev.swiftech.admin_features.students.di.studentModule
import com.ralphmarondev.swiftech.admin_features.teachers.di.teacherModule

val adminFeatureModule = listOf(
    homeModule,
    studentModule,
    teacherModule,
    coursesModule,
    evaluationModule,
    reportModule
)