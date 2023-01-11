package com.redveloper.akusisi.ui.addseller.contact

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.redveloper.akusisi.databinding.FragmentSellerContactBinding
import com.redveloper.rebator.ui.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class SellerContactFragment : BaseFragment<FragmentSellerContactBinding>() {

    val sellerViewModel: SellerContactViewModel by viewModel()

    override fun inflate(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSellerContactBinding {
        return FragmentSellerContactBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.layoutAppbar.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }


}