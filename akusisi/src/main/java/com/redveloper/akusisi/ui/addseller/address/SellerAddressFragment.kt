package com.redveloper.akusisi.ui.addseller.address

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.redveloper.akusisi.R
import com.redveloper.akusisi.databinding.FragmentSellerAddressBinding
import com.redveloper.akusisi.ui.AkusisiBaseFragment
import com.redveloper.akusisi.ui.addseller.model.AddSellerModel
import com.redveloper.rebator.utils.safeNavigate
import com.redveloper.rebator.utils.setVisility
import com.redveloper.rebator.utils.toast
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class SellerAddressFragment : AkusisiBaseFragment<FragmentSellerAddressBinding>() {

    val sellerViewModel: SellerAddressViewModel by viewModel()

    val args by navArgs<SellerAddressFragmentArgs>()

    lateinit var addSellerModel: AddSellerModel

    override fun inflate(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSellerAddressBinding {
        return FragmentSellerAddressBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inject()
        initView()
        initObserver()
        initClicklistner()
    }

    private fun initView(){
        args.addSellerModel?.let {
            addSellerModel = it
            Log.i("dataSeller", addSellerModel.toString())
        }

        lifecycleScope.launch {
            sellerViewModel.getProvinces()
        }
    }

    private fun initObserver(){
        sellerViewModel.loadingEvent.observe(viewLifecycleOwner){
            it.contentIfNotHaveBeenHandle?.let {
                binding.progressBar.setVisility(it)
            }
        }

        sellerViewModel.errorAddress.observe(viewLifecycleOwner){
            it.contentIfNotHaveBeenHandle?.let {
                requireActivity().toast(it)
            }
        }

        sellerViewModel.listProvince.observe(viewLifecycleOwner){
            it.contentIfNotHaveBeenHandle?.let {
                Log.i("dataProvince", it.toString())
            }
        }
    }

    private fun initClicklistner(){
        binding.layoutAppbar.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnSave.setOnClickListener {
            findNavController().safeNavigate(SellerAddressFragmentDirections.actionToSellerPhoto())
        }
    }
}