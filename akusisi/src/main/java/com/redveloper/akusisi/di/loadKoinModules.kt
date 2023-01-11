package com.redveloper.akusisi.di

import com.redveloper.akusisi.di.module.networkModule
import com.redveloper.akusisi.di.module.repositoryModule
import com.redveloper.akusisi.di.module.useCaseModule
import org.koin.core.context.loadKoinModules

object Inject {
    val loadKoinModules by lazy {
        loadKoinModules(
            listOf(
                viewModelModule,
                networkModule,
                useCaseModule,
                repositoryModule
            )
        )
    }
}
