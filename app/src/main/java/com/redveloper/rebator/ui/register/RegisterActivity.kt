package com.redveloper.rebator.ui.register

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.redveloper.rebator.databinding.ActivityRegisterBinding
import com.redveloper.rebator.utils.State
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterActivity : AppCompatActivity() {

    lateinit var binding: ActivityRegisterBinding

    val registerViewModel: RegisterViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSignup.setOnClickListener {
            registerViewModel.submit(
                name = binding.edtName.text.toString(),
                email = binding.edtEmail.text.toString(),
                password = binding.edtPassword.text.toString()
            )
        }

        registerViewModel.errorNameEvent.observe(this){
            it.contentIfNotHaveBeenHandle?.let {
                Toast.makeText(this, "name: $it", Toast.LENGTH_SHORT).show()
            }
        }

        registerViewModel.errorEmailEvent.observe(this){
            it.contentIfNotHaveBeenHandle?.let {
                Toast.makeText(this, "email: $it", Toast.LENGTH_SHORT).show()
            }
        }

        registerViewModel.errorPasswordEvent.observe(this){
            it.contentIfNotHaveBeenHandle?.let {
                Toast.makeText(this, "password: $it", Toast.LENGTH_SHORT).show()
            }
        }

        registerViewModel.registerResult.observe(this){ state ->
            when(state){
                is State.Loading -> {
                    binding.progress.visibility = View.VISIBLE
                }
                is State.Failed -> {
                    binding.progress.visibility = View.GONE
                    val message = state.message
                    Toast.makeText(this@RegisterActivity, "error $message", Toast.LENGTH_SHORT).show()
                }
                is State.Success -> {
                    binding.progress.visibility = View.GONE
                    val data = state.data.email
                    Toast.makeText(this@RegisterActivity, "data: $data", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    companion object{
        fun navigate(activity: Activity){
            val intent = Intent(activity, RegisterActivity::class.java)
            activity.startActivity(intent)
        }
    }
}