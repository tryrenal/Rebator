package com.redveloper.akusisi.ui.addseller.information

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.redveloper.akusisi.databinding.FragmentSellerInformationBinding
import com.redveloper.akusisi.ui.AkusisiBaseFragment
import com.redveloper.akusisi.ui.addseller.model.AddSellerModel
import com.redveloper.rebator.utils.setVisility
import com.redveloper.rebator.utils.toast
import org.koin.androidx.viewmodel.ext.android.viewModel

class SellerInformationFragment : AkusisiBaseFragment<FragmentSellerInformationBinding>() {

    val sellerViewModel: SellerInformationViewModel by viewModel()

    override fun inflate(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSellerInformationBinding {
        return FragmentSellerInformationBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inject()
        initView()
        initClicklistener()
        initObserver()
    }

    private fun initView(){
        sellerViewModel.getUser()
    }

    private fun initClicklistener(){
        binding.layoutAppbar.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnSave.setOnClickListener {
            submitData()
        }
    }

    private fun submitData(){
        val addSellerModel = AddSellerModel(
            akusisiName = binding.edtAkusisi.text.toString(),
            officeName = binding.edtOfficeName.text.toString(),
            tiktokID = binding.edtTiktokId.text.toString()
        )
        sellerViewModel.submit(addSellerModel)
    }

    private fun initObserver(){
        observerError()

        sellerViewModel.successUserEvent.observe(viewLifecycleOwner){
            it.contentIfNotHaveBeenHandle?.let { data ->
                binding.edtAkusisi.setText(data.name)
            }
        }

        sellerViewModel.loadingEvent.observe(viewLifecycleOwner){
            it.contentIfNotHaveBeenHandle?.let {
                binding.progressBar.setVisility(it)
            }
        }

        sellerViewModel.successSubmitEvent.observe(viewLifecycleOwner){
            it.contentIfNotHaveBeenHandle?.let { data ->
                val action = SellerInformationFragmentDirections.actionToSellerAddress(
                    addSellerModel = data)
                findNavController().navigate(action)
            }
        }
    }

    private fun observerError(){
        sellerViewModel.errorAkusisiNameEvent.observe(viewLifecycleOwner){
            it.contentIfNotHaveBeenHandle?.let {
                binding.edtAkusisi.error = it
            }
        }

        sellerViewModel.errorOfficeNameEvent.observe(viewLifecycleOwner){
            it.contentIfNotHaveBeenHandle?.let {
                binding.edtOfficeName.error = it
            }
        }

        sellerViewModel.errorTiktokIdEvent.observe(viewLifecycleOwner){
            it.contentIfNotHaveBeenHandle?.let {
                binding.edtTiktokId.error = it
            }
        }

        sellerViewModel.errorSubmitEvent.observe(viewLifecycleOwner){
            it.contentIfNotHaveBeenHandle?.let {
                requireActivity().toast(it)
            }
        }

        sellerViewModel.errorUserEvent.observe(viewLifecycleOwner){
            it.contentIfNotHaveBeenHandle?.let {
                requireActivity().toast(it)
            }
        }
    }

}