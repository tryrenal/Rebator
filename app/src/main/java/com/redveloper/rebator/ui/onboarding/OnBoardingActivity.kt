package com.redveloper.rebator.ui.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.redveloper.rebator.databinding.ActivityOnBoarding2Binding
import com.redveloper.rebator.ui.BaseActivity
import com.redveloper.rebator.ui.onboarding.screen.FirstScreenOnBoarding
import com.redveloper.rebator.ui.onboarding.screen.SecondScreenOnBoarding
import com.redveloper.rebator.ui.onboarding.screen.ThirdScreenOnBoarding

class OnBoardingActivity : BaseActivity<ActivityOnBoarding2Binding>() {

    override fun inflate(): ActivityOnBoarding2Binding {
        return ActivityOnBoarding2Binding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val listFragment = arrayListOf<Fragment>(
            FirstScreenOnBoarding(), SecondScreenOnBoarding(), ThirdScreenOnBoarding()
        )

        val adapter = OnBoardingPagerAdapter(listFragment, supportFragmentManager, lifecycle)
        binding.viewpager.adapter = adapter

        TabLayoutMediator(binding.tablayout, binding.viewpager){ tab, position ->
        }.attach()
    }
}