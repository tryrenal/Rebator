package com.redveloper.rebator.ui.register.createuser

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.redveloper.rebator.R
import com.redveloper.rebator.databinding.FragmentRegisterCreataUserBinding
import com.redveloper.rebator.ui.BaseFragment
import com.redveloper.rebator.utils.State
import com.redveloper.rebator.utils.gone
import com.redveloper.rebator.utils.setVisility
import com.redveloper.rebator.utils.visible
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterCreataUserFragment : BaseFragment<FragmentRegisterCreataUserBinding>() {

    val regisViewModel: RegisterCreataUserViewModel by viewModel()

    override fun inflate(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentRegisterCreataUserBinding {
        return FragmentRegisterCreataUserBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initClicklistener()
        initObserver()
    }

    private fun initView(){

    }

    private fun initClicklistener(){
        binding.btnRegister.setOnClickListener {
            regisViewModel.submit(
                email = binding.edtEmail.text.toString(),
                password = binding.edtPassword.text.toString()
            )
        }
    }

    private fun initObserver(){
        errorObserver()

        regisViewModel.loadingEvent.observe(viewLifecycleOwner){
            it.contentIfNotHaveBeenHandle?.let {
                binding.progressBar.setVisility(it)
            }
        }

        regisViewModel.successEvent.observe(viewLifecycleOwner){
            it.contentIfNotHaveBeenHandle?.let {
                Toast.makeText(requireContext(), "success: $it", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_to_registerCameraUserFragment)
            }
        }
    }

    private fun errorObserver(){
        regisViewModel.errorEvent.observe(viewLifecycleOwner){
            it.contentIfNotHaveBeenHandle?.let {
                Toast.makeText(requireContext(), "error: $it", Toast.LENGTH_SHORT).show()
            }
        }
        regisViewModel.errorEmailEvent.observe(viewLifecycleOwner){
            it.contentIfNotHaveBeenHandle?.let {
                Toast.makeText(requireContext(), "error email: $it", Toast.LENGTH_SHORT).show()
            }
        }
        regisViewModel.errorPasswordEvent.observe(viewLifecycleOwner){
            it.contentIfNotHaveBeenHandle?.let {
                Toast.makeText(requireContext(), "error password: $it", Toast.LENGTH_SHORT).show()
            }
        }
    }
}