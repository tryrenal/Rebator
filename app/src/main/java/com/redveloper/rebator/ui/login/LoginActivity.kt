package com.redveloper.rebator.ui.login

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.redveloper.rebator.databinding.ActivityLoginBinding
import com.redveloper.rebator.ui.register.RegisterActivity
import com.redveloper.rebator.utils.State
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {

    lateinit var binding : ActivityLoginBinding

    val loginViewModel: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            loginViewModel.submit(
                email = binding.edtEmail.text.toString(),
                password = binding.edtPassword.text.toString()
            )
        }

        binding.btnToRegister.setOnClickListener {
            RegisterActivity.navigate(this)
        }

        loginViewModel.errorEmailEvent.observe(this){
            it.contentIfNotHaveBeenHandle?.let {
                Toast.makeText(this, "email: $it", Toast.LENGTH_SHORT).show()
            }
        }

        loginViewModel.errorPasswordEvent.observe(this){
            it.contentIfNotHaveBeenHandle?.let{
                Toast.makeText(this, "password: $it", Toast.LENGTH_SHORT).show()
            }
        }

        loginViewModel.loginResult.observe(this){ state ->
            when(state){
                is State.Loading -> {
                    binding.progress.visibility = View.VISIBLE
                }
                is State.Failed -> {
                    binding.progress.visibility = View.GONE
                    val message = state.message
                    Toast.makeText(this@LoginActivity, "error: $message", Toast.LENGTH_SHORT).show()
                }
                is State.Success -> {
                    binding.progress.visibility = View.GONE
                    val data = state.data
                    Toast.makeText(this@LoginActivity, "data: $data", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}