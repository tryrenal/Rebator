package com.redveloper.inkubasi.di.modules

import com.redveloper.inkubasi.data.local.preference.filter.FilterPreference
import com.redveloper.inkubasi.data.local.preference.filter.FilterPreferenceImpl
import org.koin.dsl.module

val localModules = module {
    single<FilterPreference> { FilterPreferenceImpl(get()) }
}