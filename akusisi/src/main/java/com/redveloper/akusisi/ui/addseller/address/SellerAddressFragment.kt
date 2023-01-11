package com.redveloper.akusisi.ui.addseller.address

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.redveloper.akusisi.R
import com.redveloper.akusisi.databinding.FragmentSellerAddressBinding
import com.redveloper.akusisi.di.Inject
import com.redveloper.akusisi.ui.addseller.model.AddSellerModel
import com.redveloper.rebator.ui.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel


class SellerAddressFragment : BaseFragment<FragmentSellerAddressBinding>() {

    val sellerViewModel: SellerAddressViewModel by viewModel()
    fun inject() = Inject.loadKoinModules

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
    }

    private fun initObserver(){

    }

    private fun initClicklistner(){
        binding.layoutAppbar.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnSave.setOnClickListener {
            findNavController().navigate(R.id.action_to_seller_photo)
        }
    }
}