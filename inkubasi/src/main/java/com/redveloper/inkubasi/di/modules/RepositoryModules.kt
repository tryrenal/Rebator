package com.redveloper.inkubasi.di.modules

import com.redveloper.inkubasi.domain.repository.InkubasiRepository
import com.redveloper.inkubasi.domain.repository.UpdateSellerRepository
import org.koin.dsl.module

val repositoryModules = module {
    single { UpdateSellerRepository(get(), get()) }
    single { InkubasiRepository(get(), get()) }
}