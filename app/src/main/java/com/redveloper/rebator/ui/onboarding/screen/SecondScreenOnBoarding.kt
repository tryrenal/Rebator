package com.redveloper.rebator.ui.onboarding.screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.redveloper.rebator.R
import com.redveloper.rebator.databinding.FragmentSecondScreenOnBoardingBinding
import com.redveloper.rebator.ui.BaseFragment

class SecondScreenOnBoarding : BaseFragment<FragmentSecondScreenOnBoardingBinding>() {

    override fun inflate(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSecondScreenOnBoardingBinding {
        return FragmentSecondScreenOnBoardingBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}