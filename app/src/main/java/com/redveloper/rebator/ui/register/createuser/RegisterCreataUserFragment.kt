package com.redveloper.rebator.ui.register.createuser

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.redveloper.rebator.R
import com.redveloper.rebator.databinding.FragmentRegisterCreataUserBinding
import com.redveloper.rebator.ui.BaseFragment
import com.redveloper.rebator.ui.login.LoginActivity
import com.redveloper.rebator.utils.setVisility
import com.redveloper.rebator.utils.toast
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
        binding.tvLogin.text = Html.fromHtml(resources.getString(R.string.label_if_have_account))
    }

    private fun initClicklistener(){
        binding.btnRegister.setOnClickListener {
            regisViewModel.submit(
                email = binding.edtEmail.text.toString(),
                password = binding.edtPassword.text.toString()
            )
        }

        binding.tvLogin.setOnClickListener {
            LoginActivity.navigate(activity = requireActivity(), finish = true)
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
                findNavController().navigate(R.id.action_to_registerCameraUserFragment)
            }
        }
    }

    private fun errorObserver(){
        regisViewModel.errorEvent.observe(viewLifecycleOwner){
            it.contentIfNotHaveBeenHandle?.let {
                requireActivity().toast(it)
            }
        }
        regisViewModel.errorEmailEvent.observe(viewLifecycleOwner){
            it.contentIfNotHaveBeenHandle?.let {
                binding.edtEmail.error = it
            }
        }
        regisViewModel.errorPasswordEvent.observe(viewLifecycleOwner){
            it.contentIfNotHaveBeenHandle?.let {
                binding.edtPassword.error = it
            }
        }
    }
}