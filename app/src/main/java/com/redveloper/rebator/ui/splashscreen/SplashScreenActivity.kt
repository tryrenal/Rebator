package com.redveloper.rebator.ui.splashscreen

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.redveloper.rebator.MainActivity
import com.redveloper.rebator.databinding.ActivitySplashScreenBinding
import com.redveloper.rebator.ui.BaseActivity
import com.redveloper.rebator.ui.login.LoginActivity
import com.redveloper.rebator.utils.setVisility
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
            splashViewModel.checkLogin()
        }
    }

    private fun initObserver(){
        splashViewModel.loadingEvent.observe(this){
            it.contentIfNotHaveBeenHandle?.let {
                binding.progressBar.setVisility(it)
            }
        }

        splashViewModel.errorEvent.observe(this){
            it.contentIfNotHaveBeenHandle?.let {
                Toast.makeText(this, "error: $it", Toast.LENGTH_SHORT).show()
            }
        }

        splashViewModel.isLoginEvent.observe(this){
            it.contentIfNotHaveBeenHandle?.let { isLogin ->
                if (isLogin){
                    startActivity(Intent(this, MainActivity::class.java))
                } else {
                    startActivity(Intent(this, LoginActivity::class.java))
                }
            }
        }
    }
}