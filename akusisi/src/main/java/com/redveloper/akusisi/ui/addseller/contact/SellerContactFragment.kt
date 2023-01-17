package com.redveloper.akusisi.ui.addseller.contact

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.redveloper.akusisi.databinding.FragmentSellerContactBinding
import com.redveloper.akusisi.ui.AkusisiBaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class SellerContactFragment : AkusisiBaseFragment<FragmentSellerContactBinding>() {

    val sellerViewModel: SellerContactViewModel by viewModel()
    val args by navArgs<SellerContactFragmentArgs>()

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
            Log.i("dataSeller", it.toString())
        }
    }

    private fun initClicklistener(){
        binding.layoutAppbar.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun initObserver(){

    }


}