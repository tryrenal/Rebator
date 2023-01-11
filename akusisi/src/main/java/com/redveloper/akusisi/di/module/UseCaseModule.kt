package com.redveloper.akusisi.di.module

import com.redveloper.akusisi.domain.usecase.addseller.SetSellerAddressUseCase
import com.redveloper.akusisi.domain.usecase.addseller.SetSellerContactUseCase
import com.redveloper.akusisi.domain.usecase.addseller.SetSellerInformationUseCase
import com.redveloper.akusisi.domain.usecase.addseller.SetSellerOfficePhotoUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single { SetSellerAddressUseCase() }
    single { SetSellerContactUseCase() }
    single { SetSellerInformationUseCase(get(), get()) }
    single { SetSellerOfficePhotoUseCase() }
}