package com.redveloper.akusisi.ui.addseller.officephoto

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.redveloper.akusisi.R
import com.redveloper.akusisi.databinding.FragmentSellerOfficePhotoBinding
import com.redveloper.akusisi.ui.AkusisiBaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class SellerOfficePhotoFragment : AkusisiBaseFragment<FragmentSellerOfficePhotoBinding>() {

    val sellerViewModel: SellerOfficePhotoViewModel by viewModel()

    override fun inflate(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSellerOfficePhotoBinding {
        return FragmentSellerOfficePhotoBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.layoutAppbar.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnSave.setOnClickListener {
            findNavController().navigate(R.id.action_to_seller_contact)
        }
    }

}