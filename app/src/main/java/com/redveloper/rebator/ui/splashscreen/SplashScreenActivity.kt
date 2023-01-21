package com.redveloper.rebator.ui.splashscreen

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.redveloper.rebator.MainActivity
import com.redveloper.rebator.databinding.ActivitySplashScreenBinding
import com.redveloper.rebator.router.AkusisiRouter
import com.redveloper.rebator.router.InkubasiRouter
import com.redveloper.rebator.ui.BaseActivity
import com.redveloper.rebator.ui.login.LoginActivity
import com.redveloper.rebator.ui.onboarding.OnBoardingActivity
import com.redveloper.rebator.utils.setVisility
import com.redveloper.rebator.utils.toast
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashScreenActivity : BaseActivity<ActivitySplashScreenBinding>() {

    private val splashViewModel: SplashScreenViewModel by viewModel()

    override fun inflate(): ActivitySplashScreenBinding {
        return ActivitySplashScreenBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        initObserver()
    }

    private fun initView(){
        lifecycleScope.launch {
            delay(3000L)
            splashViewModel.checkLoginAndOnBoardingStatus()
        }
    }

    private fun initObserver(){
        splashViewModel.loadingEvent.observe(this){
            it.contentIfNotHaveBeenHandle?.let {
                binding.progressBar.setVisility(it)
            }
        }

        splashViewModel.errorEvent.observe(this){
            it.contentIfNotHaveBeenHandle?.let { message ->
                toast(message)
            }
        }

        splashViewModel.toUserInkubasiEvent.observe(this){
            it.contentIfNotHaveBeenHandle?.let {
                InkubasiRouter.navigate(activity = this, finish = true)
            }
        }

        splashViewModel.toUserAkusisiEvent.observe(this){
            it.contentIfNotHaveBeenHandle?.let {
                AkusisiRouter.navigate(activity = this, finish = true)
            }
        }

        splashViewModel.toOnBoardingEvent.observe(this){
            it.contentIfNotHaveBeenHandle?.let {
                OnBoardingActivity.navigate(activity = this)
            }
        }

        splashViewModel.toLoginEvent.observe(this){
            it.contentIfNotHaveBeenHandle?.let {
                LoginActivity.navigate(activity = this, finish = true)
            }
        }

        splashViewModel.errorGetUserEvent.observe(this){
            it.contentIfNotHaveBeenHandle?.let {
                toast(it)
            }
        }
    }
}