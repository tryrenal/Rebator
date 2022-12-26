package com.redveloper.rebator.ui.register

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.redveloper.rebator.databinding.ActivityRegisterBinding
import com.redveloper.rebator.domain.entity.Position
import com.redveloper.rebator.ui.register.model.RegisterModel
import com.redveloper.rebator.utils.State
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterActivity : AppCompatActivity() {

    lateinit var binding: ActivityRegisterBinding

    val registerViewModel: RegisterViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        positionDropdown()
        observerError()

        binding.btnSignup.setOnClickListener {
            val registerData = RegisterModel(
                name = binding.edtName.text.toString(),
                email = binding.edtEmail.text.toString(),
                password = binding.edtPassword.text.toString(),
                photo = "photo",
                phoneNumber = binding.edtPhonenumber.text.toString(),
                posisi = registerViewModel.positionSelected
            )
            registerViewModel.submit(registerData)
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

    private fun observerError(){
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

        registerViewModel.errorPhotoEvent.observe(this){
            it.contentIfNotHaveBeenHandle?.let {
                Toast.makeText(this, "photo: $it", Toast.LENGTH_SHORT).show()
            }
        }

        registerViewModel.errorPhoneNumberEvent.observe(this){
            it.contentIfNotHaveBeenHandle?.let {
                Toast.makeText(this, "phoneNumber: $it", Toast.LENGTH_SHORT).show()
            }
        }

        registerViewModel.errorPositionEvent.observe(this){
            it.contentIfNotHaveBeenHandle?.let {
                Toast.makeText(this, "positio: $it", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun positionDropdown(){
        val data = listOf(Position.INKUBASI.name, Position.AKUSISI.name)
        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, data)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerPosition.adapter = arrayAdapter
        binding.spinnerPosition.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                registerViewModel.positionSelected = Position.valueOf(data[p2])
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                registerViewModel.positionSelected = null
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