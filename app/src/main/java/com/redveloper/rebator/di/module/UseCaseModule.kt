package com.redveloper.rebator.di.module

import com.redveloper.rebator.domain.usecase.auth.*
import com.redveloper.rebator.domain.usecase.onboarding.GetOnBoardingUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single { GetOnBoardingUseCase(get()) }

    single { CheckLoginUseCase(get(), get()) }
    single { LoginUseCase(get(), get(), get()) }
    //register
    single { RegisterCameraUserUseCase(get(), get(), get()) }
    single { RegisterCreateUserUseCase(get(), get(), get()) }
    single { RegisterInformasiUserUseCase(get(), get(), get()) }
}