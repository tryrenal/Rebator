package com.redveloper.rebator.domain.usecase.onboarding

import com.redveloper.rebator.data.local.preference.onboarding.OnBoardingPreference
import kotlinx.coroutines.flow.collectLatest

class GetOnBoardingUseCase(
    private val onBoardingPreference: OnBoardingPreference
) {

    suspend fun getOnBoardingStatus(callback: (Boolean) -> Unit){
        onBoardingPreference.getOnBoardingStatus()
            .collectLatest {
                callback.invoke(it)
            }
    }
}