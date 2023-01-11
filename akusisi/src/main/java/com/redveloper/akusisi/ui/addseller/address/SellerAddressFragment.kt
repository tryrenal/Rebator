package com.redveloper.akusisi.ui.addseller.address

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.redveloper.akusisi.R
import com.redveloper.akusisi.databinding.FragmentSellerAddressBinding
import com.redveloper.rebator.ui.BaseFragment


class SellerAddressFragment : BaseFragment<FragmentSellerAddressBinding>() {

    override fun inflate(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSellerAddressBinding {
        return FragmentSellerAddressBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.layoutAppbar.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnSave.setOnClickListener {
            findNavController().navigate(R.id.action_to_seller_photo)
        }
    }
}