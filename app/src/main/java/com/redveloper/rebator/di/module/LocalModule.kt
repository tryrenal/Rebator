package com.redveloper.rebator.di.module

import com.redveloper.rebator.data.local.preference.UserPreference
import com.redveloper.rebator.data.local.preference.UserPreferenceImpl
import org.koin.dsl.module

val preferenceModule = module {
    single<UserPreference> { UserPreferenceImpl(get()) }
}