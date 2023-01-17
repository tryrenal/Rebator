package com.redveloper.rebator.di.module

import com.redveloper.rebator.domain.usecase.address.GetCitysUseCase
import com.redveloper.rebator.domain.usecase.address.GetProvinceUseCase
import com.redveloper.rebator.domain.usecase.auth.*
import com.redveloper.rebator.domain.usecase.user.EditUserUseCase
import com.redveloper.rebator.domain.usecase.user.GetUserUseCase
import org.koin.dsl.module

val appUseCaseModule = module {
    single { CheckLoginUseCase(get(), get()) }
    single { LoginUseCase(get(), get(), get()) }
    //register
    single { RegisterCameraUserUseCase(get(), get(), get()) }
    single { RegisterCreateUserUseCase(get(), get(), get()) }
    single { RegisterInformasiUserUseCase(get(), get(), get()) }

    single { GetUserUseCase(get(), get(), get()) }
    single { EditUserUseCase(get(), get(), get(), get()) }

    single { LogoutUseCase(get(), get(), get()) }

    //master address
    single { GetProvinceUseCase(get(), get()) }
    single { GetCitysUseCase(get(), get()) }
}