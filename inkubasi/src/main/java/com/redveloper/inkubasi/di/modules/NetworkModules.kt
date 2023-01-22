package com.redveloper.inkubasi.di.modules

import com.redveloper.inkubasi.data.network.inkubasi.InkubasiFirebase
import com.redveloper.inkubasi.data.network.inkubasi.InkubasiFirebaseImpl
import com.redveloper.inkubasi.data.network.updateseller.UpdateSeller
import com.redveloper.inkubasi.data.network.updateseller.UpdateSellerImpl
import org.koin.dsl.module

val networkModules = module {
    single<UpdateSeller> { UpdateSellerImpl(get(), get()) }
    single<InkubasiFirebase> { InkubasiFirebaseImpl(get()) }
}