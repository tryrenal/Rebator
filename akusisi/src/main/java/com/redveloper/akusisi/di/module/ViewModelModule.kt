package com.redveloper.akusisi.di

import com.redveloper.akusisi.ui.addseller.address.SellerAddressViewModel
import com.redveloper.akusisi.ui.addseller.contact.SellerContactViewModel
import com.redveloper.akusisi.ui.addseller.information.SellerInformationViewModel
import com.redveloper.akusisi.ui.addseller.officephoto.SellerOfficePhotoViewModel
import com.redveloper.akusisi.ui.dashboard.DashboardViewModel
import com.redveloper.akusisi.ui.editprofile.EditProfileViewModel
import com.redveloper.akusisi.ui.profile.ProfileViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { DashboardViewModel() }
    viewModel { ProfileViewModel(get(), get()) }
    viewModel { EditProfileViewModel(get(), get()) }

    //add seller
    viewModel { SellerInformationViewModel(get(), get()) }
    viewModel{ SellerAddressViewModel(get()) }
    viewModel { SellerOfficePhotoViewModel() }
    viewModel { SellerContactViewModel() }
}