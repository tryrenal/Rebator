package com.redveloper.rebator.di.module

import com.redveloper.rebator.domain.usecase.auth.LoginUseCase
import com.redveloper.rebator.domain.usecase.auth.RegisterCameraUserUseCase
import com.redveloper.rebator.domain.usecase.auth.RegisterCreateUserUseCase
import com.redveloper.rebator.domain.usecase.auth.RegisterInformasiUserUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single { LoginUseCase(get(), get(), get()) }
    //register
    single { RegisterCameraUserUseCase(get(), get(), get()) }
    single { RegisterCreateUserUseCase(get(), get(), get()) }
    single { RegisterInformasiUserUseCase(get(), get(), get()) }
}