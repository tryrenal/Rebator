package com.redveloper.rebator.ui.login

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.redveloper.rebator.databinding.ActivityLoginBinding
import com.redveloper.rebator.ui.BaseActivity
import com.redveloper.rebator.ui.register.RegisterActivity
import com.redveloper.rebator.utils.State
import com.redveloper.rebator.utils.setVisility
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

    }

    private fun initClicklistener(){
        binding.btnLogin.setOnClickListener {
            loginViewModel.submit(
                email = binding.edtEmail.text.toString(),
                password = binding.edtPassword.text.toString()
            )
        }

        binding.btnToRegister.setOnClickListener {
            RegisterActivity.navigate(this@LoginActivity)
        }
    }

    private fun initObserver(){
        errorObserver()

        loginViewModel.loadingEvent.observe(this){
            it.contentIfNotHaveBeenHandle?.let {
                binding.progress.setVisility(it)
            }
        }

        loginViewModel.successSubmitEvent.observe(this){
            it.contentIfNotHaveBeenHandle?.let {
                Toast.makeText(this, "success: $it", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun errorObserver(){
        loginViewModel.errorSubmitEvent.observe(this){
            it.contentIfNotHaveBeenHandle?.let {
                Toast.makeText(this, "error: $it", Toast.LENGTH_SHORT).show()
            }
        }
        loginViewModel.errorEmailEvent.observe(this){
            it.contentIfNotHaveBeenHandle?.let {
                Toast.makeText(this, "error email: $it", Toast.LENGTH_SHORT).show()
            }
        }
        loginViewModel.errorPasswordEvent.observe(this){
            it.contentIfNotHaveBeenHandle?.let {
                Toast.makeText(this, "error password: $it", Toast.LENGTH_SHORT).show()
            }
        }
    }
}