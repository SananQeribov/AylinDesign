package com.legalist.aylindesign

import com.legalist.aylindesign.viewmodel.RegisterViewModel
import com.legalist.data.repository.LoginRepository
import com.legalist.data.repository.LoginRepositoryImpl
import com.legalist.data.repository.UserRepository
import com.legalist.data.repository.UserRepositoryImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module


val appModule = module {

    single<UserRepository>(named("registerRepo")) { UserRepositoryImpl("https://66a8396353c13f22a3d21b48.mockapi.io/api/v1/", get()) }

    single<LoginRepository>(named("loginRepo")) { LoginRepositoryImpl("https://66a8396353c13f22a3d21b48.mockapi.io/api/v1/", get()) }




}

