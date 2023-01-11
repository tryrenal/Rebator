package com.redveloper.akusisi.ui.addseller.contact

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.redveloper.akusisi.databinding.FragmentSellerContactBinding
import com.redveloper.rebator.ui.BaseFragment

class SellerContactFragment : BaseFragment<FragmentSellerContactBinding>() {

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