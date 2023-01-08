package com.redveloper.akusisi.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.redveloper.akusisi.R
import com.redveloper.akusisi.databinding.FragmentProfileBinding
import com.redveloper.rebator.ui.BaseFragment

class ProfileFragment : BaseFragment<FragmentProfileBinding>() {

    override fun inflate(inflater: LayoutInflater, container: ViewGroup?): FragmentProfileBinding {
        return FragmentProfileBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}