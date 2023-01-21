package com.redveloper.inkubasi.di.modules

import com.redveloper.inkubasi.ui.dashboard.DashboardViewModel
import com.redveloper.inkubasi.ui.profile.ProfileViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { ProfileViewModel(get(), get()) }
    viewModel { DashboardViewModel(get()) }
}