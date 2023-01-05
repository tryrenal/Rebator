package com.redveloper.rebator.di.module

import com.redveloper.rebator.ui.login.LoginViewModel
import com.redveloper.rebator.ui.register.camerauser.RegisterCameraUserViewModel
import com.redveloper.rebator.ui.register.createuser.RegisterCreataUserViewModel
import com.redveloper.rebator.ui.register.informasiuser.RegisterInformasiUserViewModel
import com.redveloper.rebator.ui.splashscreen.SplashScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { SplashScreenViewModel(get()) }
    //login
    viewModel { LoginViewModel(get()) }
    //register
    viewModel { RegisterCameraUserViewModel(get()) }
    viewModel { RegisterCreataUserViewModel(get()) }
    viewModel { RegisterInformasiUserViewModel(get()) }
}