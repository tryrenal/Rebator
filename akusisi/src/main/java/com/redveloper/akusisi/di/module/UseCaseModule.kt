package com.redveloper.akusisi.di.module

import com.redveloper.akusisi.domain.usecase.addseller.SetSellerAddressUseCase
import com.redveloper.akusisi.domain.usecase.addseller.SetSellerContactUseCase
import com.redveloper.akusisi.domain.usecase.addseller.SetSellerInformationUseCase
import com.redveloper.akusisi.domain.usecase.addseller.SetSellerOfficePhotoUseCase
import com.redveloper.akusisi.domain.usecase.seller.GetDetailSellerUseCase
import com.redveloper.akusisi.domain.usecase.seller.GetSellerUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single { SetSellerAddressUseCase(get(), get()) }
    single { SetSellerContactUseCase(get(), get()) }
    single { SetSellerInformationUseCase(get(), get()) }
    single { SetSellerOfficePhotoUseCase(get(), get()) }
    single { GetSellerUseCase(get(), get()) }
    single { GetDetailSellerUseCase(get(), get()) }
}