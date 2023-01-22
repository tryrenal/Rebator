package com.redveloper.inkubasi.di.modules

import com.redveloper.inkubasi.domain.repository.InkubasiSellerRepository
import org.koin.dsl.module

val repositoryModules = module {
    single { InkubasiSellerRepository(get(), get()) }
}