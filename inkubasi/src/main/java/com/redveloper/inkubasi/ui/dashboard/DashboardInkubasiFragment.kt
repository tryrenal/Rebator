package com.redveloper.inkubasi.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.redveloper.inkubasi.databinding.FragmentDashboardInkubasiBinding
import com.redveloper.inkubasi.ui.InkubasiBaseFragment
import com.redveloper.inkubasi.ui.dashboard.model.DashboardModel
import com.redveloper.rebator.utils.setVisility
import com.redveloper.rebator.utils.toast
import org.koin.androidx.viewmodel.ext.android.viewModel

class DashboardInkubasiFragment : InkubasiBaseFragment<FragmentDashboardInkubasiBinding>() {

    override var bottomNavigationVisibility: Int = View.VISIBLE

    val dashboardInkubasiViewModel: DashboardInkubasiViewModel by viewModel()
    lateinit var dashboardInkubasiAdapter: DashboardInkubasiAdapter

    override fun inflate(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDashboardInkubasiBinding {
        return FragmentDashboardInkubasiBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initClicklistener()
        initObserver()
    }

    private fun initView(){
        dashboardInkubasiAdapter = DashboardInkubasiAdapter()
        dashboardInkubasiViewModel.getSellers()

        binding.searchDashboard.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                dashboardInkubasiViewModel.searchSeller(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    private fun setupRecyclerData(data: List<DashboardModel>){
        dashboardInkubasiAdapter.submitList(data)
        binding.recyclerDashboard.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerDashboard.adapter = dashboardInkubasiAdapter
    }

    private fun initClicklistener(){
        dashboardInkubasiAdapter.itemSelected = {
            findNavController().navigate(DashboardInkubasiFragmentDirections.actionToDetail(it))
        }
        binding.btnFilter.setOnClickListener {
            findNavController().navigate(DashboardInkubasiFragmentDirections.actionToFilter())
        }
    }

    private fun initObserver(){
        dashboardInkubasiViewModel.loadingEvent.observe(viewLifecycleOwner){
            it.contentIfNotHaveBeenHandle?.let {
                binding.progressBar.setVisility(it)
            }
        }
        dashboardInkubasiViewModel.errorMessageEvent.observe(viewLifecycleOwner){
            it.contentIfNotHaveBeenHandle?.let {
                requireActivity().toast(it)
            }
        }
        dashboardInkubasiViewModel.sellerEvent.observe(viewLifecycleOwner){
            it.contentIfNotHaveBeenHandle?.let {
                setupRecyclerData(it)
            }
        }
    }

}