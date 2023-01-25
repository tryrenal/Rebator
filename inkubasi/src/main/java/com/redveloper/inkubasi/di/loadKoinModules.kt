package com.redveloper.inkubasi.di

import com.redveloper.inkubasi.di.modules.*
import com.redveloper.rebator.di.module.appUseCaseModule
import org.koin.core.context.loadKoinModules

object Inject {
    val loadKoinModules by lazy {
        loadKoinModules(
            listOf(
                viewModelModule,
                useCaseModules,
                repositoryModules,
                appUseCaseModule,
                networkModules,
                localModules
            )
        )
    }
}