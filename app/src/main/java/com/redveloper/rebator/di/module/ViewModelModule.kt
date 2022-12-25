package com.redveloper.rebator.di.module

import com.redveloper.rebator.ui.login.LoginViewModel
import com.redveloper.rebator.ui.register.RegisterViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { LoginViewModel(get()) }
    viewModel { RegisterViewModel(get()) }
}