package com.redveloper.akusisi.ui.dashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.redveloper.akusisi.R
import com.redveloper.akusisi.databinding.FragmentDashboardBinding
import com.redveloper.rebator.ui.BaseFragment

class DashboardFragment : BaseFragment<FragmentDashboardBinding>() {

    override fun inflate(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDashboardBinding {
        return FragmentDashboardBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}