package com.redveloper.inkubasi.ui.dashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.redveloper.inkubasi.R
import com.redveloper.inkubasi.databinding.FragmentDashboardBinding
import com.redveloper.inkubasi.ui.InkubasiBaseFragment

class DashboardFragment : InkubasiBaseFragment<FragmentDashboardBinding>() {

    override var bottomNavigationVisibility: Int = View.VISIBLE

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