package com.redveloper.akusisi.ui.addseller.contact

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.redveloper.akusisi.databinding.FragmentSellerContactBinding
import com.redveloper.akusisi.ui.AkusisiActivity
import com.redveloper.akusisi.ui.AkusisiBaseFragment
import com.redveloper.akusisi.ui.addseller.model.AddSellerModel
import com.redveloper.rebator.utils.setVisility
import com.redveloper.rebator.utils.toast
import org.koin.androidx.viewmodel.ext.android.viewModel

class SellerContactFragment : AkusisiBaseFragment<FragmentSellerContactBinding>() {

    val sellerViewModel: SellerContactViewModel by viewModel()
    val args by navArgs<SellerContactFragmentArgs>()

    lateinit var addSellerModel: AddSellerModel

    override fun inflate(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSellerContactBinding {
        return FragmentSellerContactBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initClicklistener()
        initObserver()
    }

    private fun initView(){
        args.addSellerModel?.let {
            addSellerModel = it
        }
    }

    private fun initClicklistener(){
        binding.layoutAppbar.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.btnSave.setOnClickListener {
            addSellerModel.sellerName = binding.edtName.text.toString()
            addSellerModel.sellerPhoneNumber = binding.edtPhoneNumber.text.toString()

            sellerViewModel.submit(addSellerModel)
        }
    }

    private fun initObserver(){
        observerError()

        sellerViewModel.loadingEvent.observe(viewLifecycleOwner){
            it.contentIfNotHaveBeenHandle?.let {
                binding.progressBar.setVisility(it)
            }
        }

        sellerViewModel.successSubmitEvent.observe(viewLifecycleOwner){
            it.contentIfNotHaveBeenHandle?.let {
                AkusisiActivity.navigate(requireActivity(), true)
            }
        }
    }

    private fun observerError(){
        sellerViewModel.errorNameEvent.observe(viewLifecycleOwner){
            it.contentIfNotHaveBeenHandle?.let {
                binding.edtName.error = it
            }
        }

        sellerViewModel.errorPhoneNumberEvent.observe(viewLifecycleOwner){
            it.contentIfNotHaveBeenHandle?.let {
                binding.edtPhoneNumber.error = it
            }
        }

        sellerViewModel.errorSubmitEvent.observe(viewLifecycleOwner){
            it.contentIfNotHaveBeenHandle?.let {
                requireActivity().toast(it)
            }
        }
    }


}