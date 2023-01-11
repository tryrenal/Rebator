package com.redveloper.akusisi.di.module

import com.redveloper.akusisi.data.network.seller.SellerFirebase
import com.redveloper.akusisi.data.network.seller.SellerFirebaseImpl
import org.koin.dsl.module

val networkModule = module {
    single<SellerFirebase> { SellerFirebaseImpl(get(), get()) }
}