package com.redveloper.akusisi.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.redveloper.akusisi.databinding.FragmentDashboardBinding
import com.redveloper.akusisi.ui.AkusisiBaseFragment
import com.redveloper.akusisi.ui.dashboard.model.SellerModel
import com.redveloper.rebator.utils.setVisility
import com.redveloper.rebator.utils.toast
import org.koin.androidx.viewmodel.ext.android.viewModel

class DashboardFragment : AkusisiBaseFragment<FragmentDashboardBinding>() {

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
        initObserver()
        initClicklistener()
    }

    private fun initView(){
        dashboardViewModel.getSellers()
    }

    private fun setEmptyData(empty: Boolean){
        binding.btnAddSeller.setVisility(empty)
        binding.tvDescEmptySeller.setVisility(empty)
        binding.icEmpty.setVisility(empty)
        binding.layoutAddSeller.setVisility(!empty)
    }

    private fun initObserver(){
        dashboardViewModel.loadingEvent.observe(viewLifecycleOwner){
            it.contentIfNotHaveBeenHandle?.let {
                binding.progressBar.setVisility(it)
            }
        }
        dashboardViewModel.errorGetSellerEvent.observe(viewLifecycleOwner){
            it.contentIfNotHaveBeenHandle?.let {
                requireActivity().toast(it)
            }
        }
        dashboardViewModel.successGetSellerEvent.observe(viewLifecycleOwner){
            it.contentIfNotHaveBeenHandle?.let {
                setEmptyData(it.isEmpty())
                setRecyclerData(it)
            }
        }
    }

    private fun setRecyclerData(listData: List<SellerModel>){
        dashboardAdapter = DashboardAdapter()
        binding.recyclerDashboard.layoutManager = LinearLayoutManager(requireContext())
        dashboardAdapter.submitList(listData)
        binding.recyclerDashboard.adapter = dashboardAdapter
    }

    private fun initClicklistener(){
        binding.btnAddSeller.setOnClickListener {
            findNavController().navigate(DashboardFragmentDirections.actionToAddSeller())
        }

        binding.layoutAddSeller.setOnClickListener{
            findNavController().navigate(DashboardFragmentDirections.actionToAddSeller())
        }
    }

}