package com.redveloper.inkubasi.ui.updateseller

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.redveloper.inkubasi.R
import com.redveloper.inkubasi.databinding.FragmentUpdateSellerBinding
import com.redveloper.inkubasi.ui.InkubasiBaseFragment

class UpdateSellerFragment : InkubasiBaseFragment<FragmentUpdateSellerBinding>() {

    override fun inflate(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentUpdateSellerBinding {
        return FragmentUpdateSellerBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}