package com.redveloper.inkubasi.ui.filterseller

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.redveloper.inkubasi.R
import com.redveloper.inkubasi.databinding.FragmentFilterSellerBinding
import com.redveloper.inkubasi.ui.InkubasiActivity
import com.redveloper.inkubasi.ui.InkubasiBaseFragment
import com.redveloper.rebator.design.popup.SingleSelectedPopUp
import com.redveloper.rebator.domain.entity.Gender
import com.redveloper.rebator.domain.entity.StatusSeller
import com.redveloper.rebator.utils.mapper.GenderMapper
import com.redveloper.rebator.utils.mapper.StatusSellerMapper
import com.redveloper.rebator.utils.setVisility
import com.redveloper.rebator.utils.toast
import com.redveloper.rebator.utils.visible
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class FilterSellerFragment : InkubasiBaseFragment<FragmentFilterSellerBinding>() {

    val filterViewModel: FilterSellerViewModel by viewModel()
    lateinit var genderAdapter: FilterCardChooseAdapter
    lateinit var statusAdapter: FilterCardChooseAdapter

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
        setupRecyclerGender()
        setupRecyclerStatus()
    }

    private fun setupRecyclerGender(){
        genderAdapter = FilterCardChooseAdapter()
        genderAdapter.submitList(listOf(
            Pair(Gender.MAN.name, GenderMapper.getValueOfGender(Gender.MAN)),
            Pair(Gender.WOMAN.name, GenderMapper.getValueOfGender(Gender.WOMAN)),
        ))
        binding.recyclerGender.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = genderAdapter
        }

        genderAdapter.selectionChanged = {
            filterViewModel.listGender = genderAdapter.itemSelected.map {
                Gender.valueOf(it.first)
            }
        }
    }

    private fun setupRecyclerStatus(){
        statusAdapter = FilterCardChooseAdapter()
        statusAdapter.submitList(listOf(
            Pair(StatusSeller.DRAFT.name, StatusSellerMapper.getValueOfStatus(StatusSeller.DRAFT)),
            Pair(StatusSeller.CONTACTED.name, StatusSellerMapper.getValueOfStatus(StatusSeller.CONTACTED)),
            Pair(StatusSeller.VISITED.name, StatusSellerMapper.getValueOfStatus(StatusSeller.VISITED)),
        ))
        binding.recyclerStatus.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = statusAdapter
        }

        statusAdapter.selectionChanged = {
            filterViewModel.listStatus = statusAdapter.itemSelected.map {
                StatusSeller.valueOf(it.first)
            }
        }
    }

    private fun initClicklistener(){
        binding.appbar.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.appbar.btnReset.setOnClickListener {
            lifecycleScope.launch { filterViewModel.clearFilter() }
        }

        binding.edtProvince.setOnClickListener {
            lifecycleScope.launch { filterViewModel.getProvinces() }
        }

        binding.edtCity.setOnClickListener {
            lifecycleScope.launch {
                val idProvince = filterViewModel.provinceSelected?.first
                filterViewModel.getCitys(idProvince)
            }
        }

        binding.edtDistrict.setOnClickListener {
            lifecycleScope.launch {
                val idCity = filterViewModel.citySelected?.first
                filterViewModel.getDistrict(idCity)
            }
        }

        binding.btnSave.setOnClickListener {
            lifecycleScope.launch {
                filterViewModel.submit()
            }
        }
    }

    private fun initObserver(){
        observerError()
        observerArea()
        filterViewModel.loadingEvent.observe(viewLifecycleOwner){
            it.contentIfNotHaveBeenHandle?.let {
                binding.progressBar.setVisility(it)
            }
        }
        filterViewModel.successSubmitEvent.observe(viewLifecycleOwner){
            it.contentIfNotHaveBeenHandle?.let {
                InkubasiActivity.navigate(activity = requireActivity(), finish = true)
            }
        }
        filterViewModel.successResetEvent.observe(viewLifecycleOwner){
            it.contentIfNotHaveBeenHandle?.let {
                InkubasiActivity.navigate(activity = requireActivity(), finish = true)
            }
        }
    }

    private fun observerArea(){
        filterViewModel.listProvinceEvent.observe(viewLifecycleOwner){
            it.contentIfNotHaveBeenHandle?.let {
                showListProvince(it as ArrayList<Pair<String, String>>)
            }
        }
        filterViewModel.listCityEvent.observe(viewLifecycleOwner){
            it.contentIfNotHaveBeenHandle?.let {
                showListCity(it as ArrayList<Pair<String, String>>)
            }
        }
        filterViewModel.listDistrictEvent.observe(viewLifecycleOwner){
            it.contentIfNotHaveBeenHandle?.let {
                showListDistrict(it as ArrayList<Pair<String, String>>)
            }
        }
    }

    private fun observerError(){
        filterViewModel.errorMessageEvent.observe(viewLifecycleOwner){
            it.contentIfNotHaveBeenHandle?.let {
                requireActivity().toast(it)
            }
        }
        filterViewModel.errorProvinceEvent.observe(viewLifecycleOwner){
            it.contentIfNotHaveBeenHandle?.let {
                binding.edtProvince.error = it
            }
        }
        filterViewModel.errorCityEvent.observe(viewLifecycleOwner){
            it.contentIfNotHaveBeenHandle?.let {
                binding.edtCity.error = it
            }
        }
        filterViewModel.errorDistrictEvent.observe(viewLifecycleOwner){
            it.contentIfNotHaveBeenHandle?.let {
                binding.edtDistrict.error = it
            }
        }
    }

    private fun showListProvince(datas: ArrayList<Pair<String, String>>){
        val provincePopUp = SingleSelectedPopUp.create(resources.getString(com.redveloper.rebator.R.string.province), datas)
        provincePopUp.safeShow(childFragmentManager, "province pop up")
        provincePopUp.listener = object : SingleSelectedPopUp.Listener{
            override fun itemChoose(item: Pair<String, String>) {
                filterViewModel.provinceSelected = Pair(item.first.toInt(), item.second)
                binding.edtProvince.setText(item.second)
            }
        }
    }

    private fun showListCity(datas: ArrayList<Pair<String, String>>){
        val cityPopUp = SingleSelectedPopUp.create(resources.getString(com.redveloper.rebator.R.string.city), datas)
        cityPopUp.safeShow(childFragmentManager, "city pop up")
        cityPopUp.listener = object : SingleSelectedPopUp.Listener{
            override fun itemChoose(item: Pair<String, String>) {
                filterViewModel.citySelected = Pair(item.first.toInt(), item.second)
                binding.edtCity.setText(item.second)
            }
        }
    }

    private fun showListDistrict(datas: ArrayList<Pair<String, String>>){
        val districtPopUp = SingleSelectedPopUp.create(resources.getString(com.redveloper.rebator.R.string.district), datas)
        districtPopUp.safeShow(childFragmentManager, "district pop up")
        districtPopUp.listener = object : SingleSelectedPopUp.Listener{
            override fun itemChoose(item: Pair<String, String>) {
                filterViewModel.districtSelected = Pair(item.first.toInt(), item.second)
                binding.edtDistrict.setText(item.second)
            }
        }
    }


}