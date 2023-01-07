package com.redveloper.rebator.ui.onboarding

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.redveloper.rebator.databinding.ActivityOnBoarding2Binding
import com.redveloper.rebator.ui.BaseActivity
import com.redveloper.rebator.ui.login.LoginActivity
import com.redveloper.rebator.ui.onboarding.screen.FirstScreenOnBoarding
import com.redveloper.rebator.ui.onboarding.screen.SecondScreenOnBoarding
import com.redveloper.rebator.ui.onboarding.screen.ThirdScreenOnBoarding
import com.redveloper.rebator.utils.setVisility
import org.koin.androidx.viewmodel.ext.android.viewModel

class OnBoardingActivity : BaseActivity<ActivityOnBoarding2Binding>() {

    val onBoardingViewModel : OnBoardingViewModel by viewModel()

    override fun inflate(): ActivityOnBoarding2Binding {
        return ActivityOnBoarding2Binding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        initClicklistener()
        initObserver()
    }

    private fun initView(){
        val listFragment = arrayListOf<Fragment>(
            FirstScreenOnBoarding(), SecondScreenOnBoarding(), ThirdScreenOnBoarding()
        )

        val adapter = OnBoardingPagerAdapter(listFragment, supportFragmentManager, lifecycle)
        binding.viewpager.adapter = adapter

        TabLayoutMediator(binding.tablayout, binding.viewpager){ tab, position -> }.attach()
        binding.tablayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                binding.tablayout.setVisility(tab?.position != 2)
                binding.btnToLogin.setVisility(tab?.position == 2)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun initClicklistener(){
        binding.btnToLogin.setOnClickListener {
            onBoardingViewModel.setOnBoardingStatus(true)
        }
    }

    private fun initObserver(){
        onBoardingViewModel.toLoginEvent.observe(this){
            it.contentIfNotHaveBeenHandle?.let{
                LoginActivity.navigate(activity = this, finish = true)
            }
        }
    }

    companion object{
        fun navigate(activity: Activity){
            val intent = Intent(activity, OnBoardingActivity::class.java)
            activity.startActivity(intent)
            activity.finish()
        }
    }
}