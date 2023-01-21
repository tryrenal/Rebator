package com.redveloper.inkubasi.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.redveloper.inkubasi.R
import com.redveloper.inkubasi.databinding.FragmentProfileBinding
import com.redveloper.inkubasi.ui.InkubasiBaseFragment


class ProfileFragment : InkubasiBaseFragment<FragmentProfileBinding>() {

    override var bottomNavigationVisibility: Int = View.VISIBLE

    override fun inflate(inflater: LayoutInflater, container: ViewGroup?): FragmentProfileBinding {
        return FragmentProfileBinding.inflate(inflater, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}