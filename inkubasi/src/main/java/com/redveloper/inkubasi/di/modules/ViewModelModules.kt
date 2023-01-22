package com.redveloper.inkubasi.di.modules

import com.redveloper.inkubasi.ui.dashboard.DashboardInkubasiViewModel
import com.redveloper.inkubasi.ui.detailseller.DetailSellerViewModel
import com.redveloper.inkubasi.ui.profile.ProfileViewModel
import com.redveloper.inkubasi.ui.updateseller.UpdateSellerViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { ProfileViewModel(get(), get()) }
    viewModel { DashboardInkubasiViewModel(get()) }
    viewModel{ DetailSellerViewModel(get()) }
    viewModel { UpdateSellerViewModel(get(), get(), get()) }
}