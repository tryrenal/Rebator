package com.redveloper.rebator.ui.onboarding.screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.redveloper.rebator.R
import com.redveloper.rebator.databinding.FragmentFirstScreenOnBoardingBinding
import com.redveloper.rebator.ui.BaseFragment

class FirstScreenOnBoarding : BaseFragment<FragmentFirstScreenOnBoardingBinding>() {

    override fun inflate(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentFirstScreenOnBoardingBinding {
        return FragmentFirstScreenOnBoardingBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}