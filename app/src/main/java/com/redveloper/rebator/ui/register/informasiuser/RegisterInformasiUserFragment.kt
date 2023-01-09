package com.redveloper.rebator.ui.register.informasiuser

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.redveloper.rebator.MainActivity
import com.redveloper.rebator.R
import com.redveloper.rebator.databinding.FragmentRegisterInformasiUserBinding
import com.redveloper.rebator.design.popup.SingleSelectedPopUp
import com.redveloper.rebator.domain.entity.Position
import com.redveloper.rebator.router.AkusisiRouter
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
        binding.edtPosition.setOnClickListener {
            showPositionChoosen()
        }
    }

    private fun showPositionChoosen(){
        val listData = arrayListOf<Pair<String, String>>(
            Pair(Position.AKUSISI.name, "Akusisi"),
            Pair(Position.INKUBASI.name, "Inkubasi"),
        )
        val singlePopUp = SingleSelectedPopUp.create(resources.getString(R.string.position), listData)
        singlePopUp.safeShow(childFragmentManager, "single pop up")
        singlePopUp.listener = object : SingleSelectedPopUp.Listener{
            override fun itemChoose(item: Pair<String, String>) {
                regisViewModel.positionSelected = Position.valueOf(item.first)
                binding.edtPosition.setText(item.second)
            }
        }
    }

    private fun initObserver(){
        errorObserver()

        regisViewModel.loadingEvent.observe(viewLifecycleOwner){
            it.contentIfNotHaveBeenHandle?.let {
                binding.progressBar.setVisility(it)
            }
        }

        regisViewModel.toUserInkubasiEvent.observe(viewLifecycleOwner){
            it.contentIfNotHaveBeenHandle?.let {
                requireActivity().toast("to user inkubasi")
            }
        }

        regisViewModel.toUserAkusisiEvent.observe(viewLifecycleOwner){
            it.contentIfNotHaveBeenHandle?.let {
                AkusisiRouter.navigate(activity = requireActivity(), finish = true)
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

        regisViewModel.errorGetUserEvent.observe(viewLifecycleOwner){
            it.contentIfNotHaveBeenHandle?.let {
                requireActivity().toast(it)
            }
        }
    }

}