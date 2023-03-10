package com.redveloper.inkubasi.ui.detailseller

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.redveloper.inkubasi.databinding.FragmentDetailSellerBinding
import com.redveloper.inkubasi.domain.entity.Inkubasi
import com.redveloper.inkubasi.ui.InkubasiBaseFragment
import com.redveloper.inkubasi.ui.detailseller.model.DetailSellerModel
import com.redveloper.inkubasi.utils.mapper.PotentialSellerMapper
import com.redveloper.inkubasi.utils.mapper.ResultVisitMapper
import com.redveloper.rebator.utils.date.DateUtils
import com.redveloper.rebator.utils.image.load
import com.redveloper.rebator.utils.mapper.GenderMapper
import com.redveloper.rebator.utils.mapper.StatusSellerMapper
import com.redveloper.rebator.utils.setVisility
import com.redveloper.rebator.utils.toast
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailSellerFragment : InkubasiBaseFragment<FragmentDetailSellerBinding>() {

    val detailViewModel: DetailSellerViewModel by viewModel()
    val args by navArgs<DetailSellerFragmentArgs>()

    override fun inflate(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDetailSellerBinding {
        return FragmentDetailSellerBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initClicklistener()
        initObserver()
    }

    private fun initView(){
        args.tiktokId.let {
            detailViewModel.tiktokId = it
            detailViewModel.getSeller(it)
            detailViewModel.getInkubasi(it)
        }
    }

    private fun initClicklistener(){
        binding.appbar.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.btnNext.setOnClickListener {
            detailViewModel.tiktokId?.let {
                findNavController().navigate(DetailSellerFragmentDirections.actionDetailSellerFragmentToUpdateSellerFragment(it))
            }
        }
    }

    private fun initObserver(){
        detailViewModel.loadingEvent.observe(viewLifecycleOwner){
            it.contentIfNotHaveBeenHandle?.let {
                binding.progressBar.setVisility(it)
            }
        }
        detailViewModel.errorEvent.observe(viewLifecycleOwner){
            it.contentIfNotHaveBeenHandle?.let {
                requireActivity().toast(it)
            }
        }
        detailViewModel.getUserEvent.observe(viewLifecycleOwner){
            it.contentIfNotHaveBeenHandle?.let {
                setSellerData(it)
            }
        }
        detailViewModel.inkubasiEvent.observe(viewLifecycleOwner){
            it.contentIfNotHaveBeenHandle?.let {
                setInkubasiData(it)
            }
        }
    }

    private fun setInkubasiData(data: Inkubasi){
        binding.layoutStatus.root.setVisility(data.visit != null && data.potential != null)
        binding.layoutStatus.apply {
            tvVisit.text = data.visit?.let { ResultVisitMapper.getValueOfResultVisit(it) }
            tvPotential.text = data.potential?.let { PotentialSellerMapper.getValueOfPotential(it) }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setSellerData(data: DetailSellerModel){
        data.photoUrl?.let { binding.imgSeller.load(it) }
        binding.tvStatus.text = data.status?.let { StatusSellerMapper.getValueOfStatus(it) }
        binding.tvJoinDate.text = data.joinDate?.let { DateUtils.convertToString(it) }
        binding.tvAddress.text = data.officeAddress
        binding.tvProvince.text = data.officeProvince
        binding.tvCity.text = data.officeCity
        binding.tvDistrict.text = data.officeDistrict
        binding.tvSellerName.text = ": "+data.sellerName
        binding.tvPhoneNumber.text = ": "+data.sellerPhoneNumber
        binding.tvGender.text = ": "+data.sellerGender?.let { GenderMapper.getValueOfGender(it) }
    }

}