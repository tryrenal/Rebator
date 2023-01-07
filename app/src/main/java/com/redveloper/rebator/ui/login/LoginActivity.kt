package com.redveloper.rebator.ui.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.redveloper.rebator.MainActivity
import com.redveloper.rebator.R
import com.redveloper.rebator.databinding.ActivityLoginBinding
import com.redveloper.rebator.ui.BaseActivity
import com.redveloper.rebator.ui.register.RegisterActivity
import com.redveloper.rebator.utils.State
import com.redveloper.rebator.utils.setVisility
import com.redveloper.rebator.utils.toast
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : BaseActivity<ActivityLoginBinding>() {

    override fun inflate(): ActivityLoginBinding {
        return ActivityLoginBinding.inflate(layoutInflater)
    }

    val loginViewModel: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        initClicklistener()
        initObserver()
    }

    private fun initView(){
        binding.tvSignUp.text = Html.fromHtml(resources.getString(R.string.label_if_dont_have_account))
    }

    private fun initClicklistener(){
        binding.btnLogin.setOnClickListener {
            loginViewModel.submit(
                email = binding.edtEmail.text.toString(),
                password = binding.edtPassword.text.toString()
            )
        }

        binding.tvSignUp.setOnClickListener {
            RegisterActivity.navigate(this@LoginActivity)
        }
    }

    private fun initObserver(){
        errorObserver()

        loginViewModel.loadingEvent.observe(this){
            it.contentIfNotHaveBeenHandle?.let {
                binding.progressBar.setVisility(it)
            }
        }

        loginViewModel.successSubmitEvent.observe(this){
            it.contentIfNotHaveBeenHandle?.let {
                startActivity(Intent(this, MainActivity::class.java))
            }
        }
    }

    private fun errorObserver(){
        loginViewModel.errorSubmitEvent.observe(this){
            it.contentIfNotHaveBeenHandle?.let {
                toast(it)
            }
        }
        loginViewModel.errorEmailEvent.observe(this){
            it.contentIfNotHaveBeenHandle?.let {
                binding.edtEmail.error = it
            }
        }
        loginViewModel.errorPasswordEvent.observe(this){
            it.contentIfNotHaveBeenHandle?.let {
                binding.edtPassword.error = it
            }
        }
    }

    companion object{
        fun navigate(activity: Activity, finish: Boolean = false){
            val intent = Intent(activity, LoginActivity::class.java)
            activity.startActivity(intent)
            if (finish){
                activity.finish()
            }
        }
    }
}