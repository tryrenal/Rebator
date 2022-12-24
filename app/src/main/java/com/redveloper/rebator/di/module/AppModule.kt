package com.redveloper.rebator.di.module

import com.redveloper.rebator.utils.dispatchers.CrDispatcher
import com.redveloper.rebator.utils.dispatchers.CrDispatherImpl
import org.koin.dsl.module

val appModule = module{
    single<CrDispatcher> { CrDispatherImpl() }
}