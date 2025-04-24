package com.ralphmarondev.swiftech.auth.di

import com.ralphmarondev.swiftech.auth.presentation.login.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val authModule = module {
    viewModelOf(::LoginViewModel)
}