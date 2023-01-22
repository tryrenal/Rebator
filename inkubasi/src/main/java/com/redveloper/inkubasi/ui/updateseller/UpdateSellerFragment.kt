package com.redveloper.inkubasi.ui.updateseller

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.redveloper.inkubasi.R as InkubasiR
import com.redveloper.rebator.R as AppR
import com.redveloper.inkubasi.databinding.FragmentUpdateSellerBinding
import com.redveloper.inkubasi.ui.InkubasiBaseFragment
import com.redveloper.rebator.design.popup.SingleSelectedPopUp
import org.koin.androidx.viewmodel.ext.android.viewModel

class UpdateSellerFragment : InkubasiBaseFragment<FragmentUpdateSellerBinding>() {

    val updateViewModel: UpdateSellerViewModel by viewModel()
    val args by navArgs<UpdateSellerFragmentArgs>()

    override fun inflate(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentUpdateSellerBinding {
        return FragmentUpdateSellerBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initObserver()
        initClicklistener()
    }

    private fun initView(){

    }

    private fun initClicklistener(){
        binding.edtPotential.setOnClickListener {
            updateViewModel.getListPotential()
        }
        binding.edtVisit.setOnClickListener {
            updateViewModel.getListResultVisit()
        }
        binding.edtStatus.setOnClickListener {
            updateViewModel.getListStatus()
        }
    }

    private fun initObserver(){
        updateViewModel.listPotentialEvent.observe(viewLifecycleOwner){
            it.contentIfNotHaveBeenHandle?.let {
                showPopUpPotential(it)
            }
        }
        updateViewModel.listResultVisitEvent.observe(viewLifecycleOwner){
            it.contentIfNotHaveBeenHandle?.let {
                showPopUpResultVisit(it)
            }
        }
        updateViewModel.listStatusSellerEvent.observe(viewLifecycleOwner){
            it.contentIfNotHaveBeenHandle?.let {
                showPopUpStatus(it)
            }
        }
    }

    private fun showPopUpPotential(data: ArrayList<Pair<String, String>>){
        val singlePopUp = SingleSelectedPopUp.create(
            title = resources.getString(InkubasiR.string.seller_potential),
            list = data
        )
        singlePopUp.safeShow(childFragmentManager, "potential")
        singlePopUp.listener = object : SingleSelectedPopUp.Listener{
            override fun itemChoose(item: Pair<String, String>) {
                binding.edtPotential.setText(item.second)
                updateViewModel.potentialSellerSelected = item
            }
        }
    }

    private fun showPopUpResultVisit(data: ArrayList<Pair<String, String>>){
        val singlePopUp = SingleSelectedPopUp.create(
            title = resources.getString(InkubasiR.string.result_visit),
            list = data
        )
        singlePopUp.safeShow(childFragmentManager, "result visit")
        singlePopUp.listener = object : SingleSelectedPopUp.Listener{
            override fun itemChoose(item: Pair<String, String>) {
                binding.edtVisit.setText(item.second)
                updateViewModel.resultVisitSelected = item
            }
        }
    }

    private fun showPopUpStatus(data: ArrayList<Pair<String, String>>){
        val singlePopUp = SingleSelectedPopUp.create(
            title = resources.getString(InkubasiR.string.status),
            list = data
        )
        singlePopUp.safeShow(childFragmentManager, "status")
        singlePopUp.listener = object : SingleSelectedPopUp.Listener{
            override fun itemChoose(item: Pair<String, String>) {
                binding.edtStatus.setText(item.second)
                updateViewModel.statusSellerSelected = item
            }
        }
    }


}