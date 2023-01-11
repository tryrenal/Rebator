package com.redveloper.akusisi.di.module

import com.redveloper.akusisi.domain.repository.SellerRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { SellerRepository(get(), get()) }
}