package com.redveloper.inkubasi.ui.dashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.redveloper.inkubasi.R
import com.redveloper.inkubasi.databinding.FragmentDashboardBinding
import com.redveloper.inkubasi.ui.InkubasiBaseFragment
import com.redveloper.inkubasi.ui.dashboard.model.DashboardModel
import com.redveloper.rebator.utils.setVisility
import com.redveloper.rebator.utils.toast
import org.koin.androidx.viewmodel.ext.android.viewModel

class DashboardFragment : InkubasiBaseFragment<FragmentDashboardBinding>() {

    override var bottomNavigationVisibility: Int = View.VISIBLE

    val dashboardViewModel: DashboardViewModel by viewModel()
    lateinit var dashboardAdapter: DashboardAdapter

    override fun inflate(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDashboardBinding {
        return FragmentDashboardBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initClicklistener()
        initObserver()
    }

    private fun initView(){
        dashboardAdapter = DashboardAdapter()
        dashboardViewModel.getSellers()
    }

    private fun setupRecyclerData(data: List<DashboardModel>){
        dashboardAdapter.submitList(data)
        binding.recyclerDashboard.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerDashboard.adapter = dashboardAdapter
    }

    private fun initClicklistener(){

    }

    private fun initObserver(){
        dashboardViewModel.loadingEvent.observe(viewLifecycleOwner){
            it.contentIfNotHaveBeenHandle?.let {
                binding.progressBar.setVisility(it)
            }
        }
        dashboardViewModel.errorMessageEvent.observe(viewLifecycleOwner){
            it.contentIfNotHaveBeenHandle?.let {
                requireActivity().toast(it)
            }
        }
        dashboardViewModel.sellerEvent.observe(viewLifecycleOwner){
            it.contentIfNotHaveBeenHandle?.let {
                setupRecyclerData(it)
            }
        }
    }

}