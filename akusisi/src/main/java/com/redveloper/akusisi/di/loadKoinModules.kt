package com.redveloper.akusisi.di

import org.koin.core.context.loadKoinModules

object Inject {
    val loadKoinModules by lazy {
        loadKoinModules(viewModelModule)
    }
}
