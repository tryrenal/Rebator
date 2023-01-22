package com.redveloper.inkubasi.di.modules

import com.redveloper.inkubasi.domain.usecase.updateseller.GetPotentialSellerUseCase
import com.redveloper.inkubasi.domain.usecase.updateseller.GetResultVisitUseCase
import com.redveloper.inkubasi.domain.usecase.updateseller.GetStatusSellerUseCase
import com.redveloper.inkubasi.domain.usecase.updateseller.UpdateSellerUseCase
import org.koin.dsl.module

val useCaseModules = module {
    single { GetPotentialSellerUseCase() }
    single { GetResultVisitUseCase() }
    single { GetStatusSellerUseCase() }
    single { UpdateSellerUseCase(get(), get()) }
}