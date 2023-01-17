package com.redveloper.akusisi.ui.addseller.address

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.redveloper.akusisi.R
import com.redveloper.akusisi.databinding.FragmentSellerAddressBinding
import com.redveloper.akusisi.ui.AkusisiBaseFragment
import com.redveloper.akusisi.ui.addseller.model.AddSellerModel
import com.redveloper.rebator.design.popup.SingleSelectedPopUp
import com.redveloper.rebator.utils.safeNavigate
import com.redveloper.rebator.utils.setVisility
import com.redveloper.rebator.utils.toast
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.redveloper.rebator.R as AppR


class SellerAddressFragment : AkusisiBaseFragment<FragmentSellerAddressBinding>() {

    val sellerViewModel: SellerAddressViewModel by viewModel()

    val args by navArgs<SellerAddressFragmentArgs>()

    var addSellerModel: AddSellerModel = AddSellerModel()

    override fun inflate(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSellerAddressBinding {
        return FragmentSellerAddressBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inject()
        initView()
        initObserver()
        initClicklistner()
    }

    private fun initView(){
        args.addSellerModel?.let {
            addSellerModel = it
        }
    }

    private fun initObserver(){
        observerError()
        observerArea()
        sellerViewModel.loadingEvent.observe(viewLifecycleOwner){
            it.contentIfNotHaveBeenHandle?.let {
                binding.progressBar.setVisility(it)
            }
        }

        sellerViewModel.successSubmitEvent.observe(viewLifecycleOwner){
            it.contentIfNotHaveBeenHandle?.let {
                findNavController().navigate(SellerAddressFragmentDirections.actionToSellerPhoto(addSellerModel))
            }
        }

    }

    private fun observerError(){
        sellerViewModel.errorAddress.observe(viewLifecycleOwner){
            it.contentIfNotHaveBeenHandle?.let {
                requireActivity().toast(it)
            }
        }

        sellerViewModel.errorAddressEvent.observe(viewLifecycleOwner){
            it.contentIfNotHaveBeenHandle?.let {
                binding.edtAddress.error = it
            }
        }

        sellerViewModel.errorProvinceEvent.observe(viewLifecycleOwner){
            it.contentIfNotHaveBeenHandle?.let {
                binding.edtProvince.error = it
            }
        }

        sellerViewModel.errorCityEvent.observe(viewLifecycleOwner){
            it.contentIfNotHaveBeenHandle?.let {
                binding.edtCity.error = it
            }
        }

        sellerViewModel.errorDistrictEvent.observe(viewLifecycleOwner){
            it.contentIfNotHaveBeenHandle?.let {
                binding.edtDistrict.error = it
            }
        }
    }

    private fun observerArea(){
        sellerViewModel.listProvince.observe(viewLifecycleOwner){
            it.contentIfNotHaveBeenHandle?.let {
                showListProvince(it as ArrayList<Pair<String, String>>)
            }
        }

        sellerViewModel.listCity.observe(viewLifecycleOwner){
            it.contentIfNotHaveBeenHandle?.let {
                showListCity(it as ArrayList<Pair<String, String>>)
            }
        }

        sellerViewModel.listDistrict.observe(viewLifecycleOwner){
            it.contentIfNotHaveBeenHandle?.let {
                showListDistrict(it as ArrayList<Pair<String, String>>)
            }
        }
    }

    private fun initClicklistner(){
        binding.layoutAppbar.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnSave.setOnClickListener {
            sellerViewModel.submit(
                address = binding.edtAddress.text.toString()
            )
        }

        binding.edtProvince.setOnClickListener {
            lifecycleScope.launch{
                sellerViewModel.getProvinces()
            }
        }

        binding.edtCity.setOnClickListener {
            lifecycleScope.launch{
                val provinceId = sellerViewModel.provinceSelected?.first
                sellerViewModel.getCitys(provinceId)
            }
        }

        binding.edtDistrict.setOnClickListener {
            lifecycleScope.launch {
                val cityId = sellerViewModel.citySelected?.first
                sellerViewModel.getDistrict(cityId)
            }
        }
    }

    private fun showListProvince(datas: ArrayList<Pair<String, String>>){
        val provincePopUp = SingleSelectedPopUp.create(resources.getString(AppR.string.province), datas)
        provincePopUp.safeShow(childFragmentManager, "province pop up")
        provincePopUp.listener = object : SingleSelectedPopUp.Listener{
            override fun itemChoose(item: Pair<String, String>) {
                sellerViewModel.provinceSelected = Pair(item.first.toInt(), item.second)
                binding.edtProvince.setText(item.second)
            }
        }
    }

    private fun showListCity(datas: ArrayList<Pair<String, String>>){
        val cityPopUp = SingleSelectedPopUp.create(resources.getString(AppR.string.city), datas)
        cityPopUp.safeShow(childFragmentManager, "city pop up")
        cityPopUp.listener = object : SingleSelectedPopUp.Listener{
            override fun itemChoose(item: Pair<String, String>) {
                sellerViewModel.citySelected = Pair(item.first.toInt(), item.second)
                binding.edtCity.setText(item.second)
            }
        }
    }

    private fun showListDistrict(datas: ArrayList<Pair<String, String>>){
        val districtPopUp = SingleSelectedPopUp.create(resources.getString(AppR.string.district), datas)
        districtPopUp.safeShow(childFragmentManager, "district pop up")
        districtPopUp.listener = object : SingleSelectedPopUp.Listener{
            override fun itemChoose(item: Pair<String, String>) {
                sellerViewModel.districtSelected = Pair(item.first.toInt(), item.second)
                binding.edtDistrict.setText(item.second)
            }
        }
    }
}