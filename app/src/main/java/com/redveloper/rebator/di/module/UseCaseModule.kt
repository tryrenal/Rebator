package com.redveloper.rebator.di.module

import com.redveloper.rebator.domain.usecase.auth.LoginUseCase
import com.redveloper.rebator.domain.usecase.auth.RegisterUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single { LoginUseCase(get(), get()) }
    single { RegisterUseCase(get(), get()) }
}