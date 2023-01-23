package com.redveloper.inkubasi.ui.filterseller

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.redveloper.inkubasi.R
import com.redveloper.inkubasi.databinding.FragmentFilterSellerBinding
import com.redveloper.inkubasi.ui.InkubasiBaseFragment
import com.redveloper.rebator.utils.visible

class FilterSellerFragment : InkubasiBaseFragment<FragmentFilterSellerBinding>() {

    override fun inflate(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentFilterSellerBinding {
        return FragmentFilterSellerBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initClicklistener()
        initObserver()
    }

    private fun initView(){
        binding.appbar.btnReset.visible()

    }

    private fun initClicklistener(){
        binding.appbar.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun initObserver(){

    }


}