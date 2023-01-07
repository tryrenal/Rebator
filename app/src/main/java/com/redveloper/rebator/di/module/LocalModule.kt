package com.redveloper.rebator.di.module

import com.redveloper.rebator.data.local.preference.UserPreference
import com.redveloper.rebator.data.local.preference.UserPreferenceImpl
import com.redveloper.rebator.data.local.preference.onboarding.OnBoardingPreference
import com.redveloper.rebator.data.local.preference.onboarding.OnBoardingPreferenceImpl
import org.koin.dsl.module

val preferenceModule = module {
    single<UserPreference> { UserPreferenceImpl(get()) }
    single<OnBoardingPreference> { OnBoardingPreferenceImpl(get()) }
}