package com.redveloper.rebator.di.module

import com.redveloper.rebator.domain.repository.AuthRepository
import com.redveloper.rebator.domain.repository.UserRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { UserRepository(get(), get()) }
    single { AuthRepository(get(), get()) }
}