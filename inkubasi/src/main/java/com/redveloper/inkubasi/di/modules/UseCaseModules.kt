package com.redveloper.inkubasi.di.modules

import com.redveloper.inkubasi.domain.usecase.filter.ClearFilterUseCase
import com.redveloper.inkubasi.domain.usecase.filter.GetFilterUseCase
import com.redveloper.inkubasi.domain.usecase.filter.SaveFilterUseCase
import com.redveloper.inkubasi.domain.usecase.inkubasi.GetDetailSellerInkubasiUseCase
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
    single { GetDetailSellerInkubasiUseCase(get(), get())}
    single { SaveFilterUseCase(get(), get())}
    single { GetFilterUseCase(get(), get()) }
    single { ClearFilterUseCase(get(), get()) }
}