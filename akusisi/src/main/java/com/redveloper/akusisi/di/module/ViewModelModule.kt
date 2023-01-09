package com.redveloper.akusisi.di

import com.redveloper.akusisi.ui.dashboard.DashboardViewModel
import com.redveloper.akusisi.ui.profile.ProfileViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { DashboardViewModel() }
    viewModel { ProfileViewModel(get()) }
}