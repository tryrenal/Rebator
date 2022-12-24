package com.redveloper.rebator.di.module

import com.redveloper.rebator.domain.usecase.auth.LoginUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single { LoginUseCase(get(), get()) }
}