package com.ralphmarondev.swiftech.student_features.di

import com.ralphmarondev.swiftech.student_features.evaluate.di.evaluateModule
import com.ralphmarondev.swiftech.student_features.evaluation_forms.di.evaluationFormsModule
import com.ralphmarondev.swiftech.student_features.home.di.homeModule
import com.ralphmarondev.swiftech.student_features.student_details.di.studentDetailModule

val studentFeatureModule = listOf(
    homeModule,
    evaluateModule,
    evaluationFormsModule,
    studentDetailModule
)