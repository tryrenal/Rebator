package com.redveloper.rebator.ui.register.informasiuser

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.redveloper.rebator.MainActivity
import com.redveloper.rebator.databinding.FragmentRegisterInformasiUserBinding
import com.redveloper.rebator.ui.BaseFragment
import com.redveloper.rebator.ui.register.informasiuser.model.RegisterInformasiUserModel
import com.redveloper.rebator.utils.setVisility
import com.redveloper.rebator.utils.toast
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterInformasiUserFragment : BaseFragment<FragmentRegisterInformasiUserBinding>() {

    val regisViewModel: RegisterInformasiUserViewModel by viewModel()

    override fun inflate(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentRegisterInformasiUserBinding {
        return FragmentRegisterInformasiUserBinding.inflate(inflater, container,false)
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
        binding.btnSave.setOnClickListener {
            val data = RegisterInformasiUserModel(
                name = binding.edtName.text.toString(),
                phoneNumber = binding.edtPhoneNumber.text.toString(),
                position = regisViewModel.positionSelected
            )
            regisViewModel.submit(data)
        }
        binding.appbar.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun initObserver(){
        errorObserver()

        regisViewModel.loadingEvent.observe(viewLifecycleOwner){
            it.contentIfNotHaveBeenHandle?.let {
                binding.progressBar.setVisility(it)
            }
        }

        regisViewModel.successSubmitEvent.observe(viewLifecycleOwner){
            it.contentIfNotHaveBeenHandle?.let {
                activity?.startActivity(Intent(requireContext(), MainActivity::class.java))
            }
        }
    }

    private fun errorObserver(){
        regisViewModel.errorSubmitEvent.observe(viewLifecycleOwner){
            it.contentIfNotHaveBeenHandle?.let {
                requireActivity().toast(it)
            }
        }
        regisViewModel.errorNameEvent.observe(viewLifecycleOwner){
            it.contentIfNotHaveBeenHandle?.let {
                binding.edtName.error = it
            }
        }

        regisViewModel.errorPhoneNumberEvent.observe(viewLifecycleOwner){
            it.contentIfNotHaveBeenHandle?.let {
                binding.edtPhoneNumber.error = it
            }
        }

        regisViewModel.errorPositionEvent.observe(viewLifecycleOwner){
            it.contentIfNotHaveBeenHandle?.let {
                binding.edtPosition.error = it
            }
        }
    }

}