package com.redveloper.akusisi.ui.seller.officephoto

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.redveloper.akusisi.R
import com.redveloper.akusisi.databinding.FragmentSellerOfficePhotoBinding
import com.redveloper.rebator.ui.BaseFragment

class SellerOfficePhotoFragment : BaseFragment<FragmentSellerOfficePhotoBinding>() {

    override fun inflate(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSellerOfficePhotoBinding {
        return FragmentSellerOfficePhotoBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}