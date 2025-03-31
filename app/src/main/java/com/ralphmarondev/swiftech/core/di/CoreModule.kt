package com.ralphmarondev.swiftech.core.di

import com.ralphmarondev.swiftech.core.data.local.database.AppDatabase
import com.ralphmarondev.swiftech.core.data.local.preferences.AppPreferences
import com.ralphmarondev.swiftech.core.data.repositories.UserRepositoryImpl
import com.ralphmarondev.swiftech.core.domain.repositories.UserRepository
import com.ralphmarondev.swiftech.core.domain.usecases.CreateUserUseCase
import com.ralphmarondev.swiftech.core.domain.usecases.DeleteUserUseCase
import com.ralphmarondev.swiftech.core.domain.usecases.GetAllUserByRoleUseCase
import com.ralphmarondev.swiftech.core.domain.usecases.GetAllUserUseCase
import com.ralphmarondev.swiftech.core.domain.usecases.GetUserDetailByUsername
import com.ralphmarondev.swiftech.core.domain.usecases.IsUserExistsUseCase
import com.ralphmarondev.swiftech.core.domain.usecases.UpdateUserUseCase
import com.ralphmarondev.swiftech.core.util.ThemeState
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val coreModule = module {
    singleOf(::AppPreferences)
    singleOf(::ThemeState)

    single { AppDatabase.createDatabase(androidContext()) }
    single { get<AppDatabase>().userDao }
    single<UserRepository> { UserRepositoryImpl(get()) }

    factoryOf(::CreateUserUseCase)
    factoryOf(::UpdateUserUseCase)
    factoryOf(::DeleteUserUseCase)
    factoryOf(::GetUserDetailByUsername)
    factoryOf(::IsUserExistsUseCase)
    factoryOf(::GetAllUserUseCase)
    factoryOf(::GetAllUserByRoleUseCase)
}