package com.redveloper.akusisi.ui.detailseller

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.redveloper.akusisi.databinding.FragmentDetailSellerBinding
import com.redveloper.akusisi.ui.AkusisiBaseFragment
import com.redveloper.akusisi.utils.ColorUtils
import com.redveloper.rebator.domain.entity.Seller
import com.redveloper.rebator.utils.date.DateUtils
import com.redveloper.rebator.utils.image.load
import com.redveloper.rebator.utils.mapper.GenderMapper
import com.redveloper.rebator.utils.setVisility
import com.redveloper.rebator.utils.toast
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailSellerFragment : AkusisiBaseFragment<FragmentDetailSellerBinding>() {

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
        args.tiktokId.let{
            detailViewModel.getDetailSeller(it)
        }
    }

    private fun initClicklistener(){
        binding.appbar.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun initObserver(){
        detailViewModel.loadingEvent.observe(viewLifecycleOwner){
            it.contentIfNotHaveBeenHandle?.let {
                binding.progressBar.setVisility(it)
            }
        }
        detailViewModel.errorTiktokIdEvent.observe(viewLifecycleOwner){
            it.contentIfNotHaveBeenHandle?.let {
                requireActivity().toast(it)
                findNavController().navigateUp()
            }
        }
        detailViewModel.errorDetailEvent.observe(viewLifecycleOwner){
            it.contentIfNotHaveBeenHandle?.let{
                requireActivity().toast(it)
            }
        }
        detailViewModel.successDetailEvent.observe(viewLifecycleOwner){
            it.contentIfNotHaveBeenHandle?.let {
                setData(it)
                it.timeStamp?.let {
                    binding.viewBottom.setBackgroundColor(ContextCompat.getColor(requireContext(), ColorUtils.setCollorView(it)))
                    binding.layoutTiktokId.setCardBackgroundColor(ContextCompat.getColor(requireContext(), ColorUtils.setCollorView(it)))
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setData(data: Seller){
        binding.tvDate.text = data.timeStamp?.let { DateUtils.convertToString(it) }
        binding.tvTiktokId.text = data.tiktokId
        data.officePhotoUrl?.let { binding.imgSeller.load(it) }
        binding.tvAkusisi.text = data.akusisiName
        binding.tvSellerName.text = ": "+data.sellerName
        binding.tvPhoneNumber.text =": "+ data.sellerPhoneNumber
        binding.tvGender.text = ": "+data.sellerGender?.let { GenderMapper.getValueOfGender(it) }
        binding.tvAddress.text = data.officeAddress
        binding.tvProvince.text = ": "+data.officeProvinceName
        binding.tvCity.text = ": "+data.officeCityName
        binding.tvDistrict.text = ": "+data.officeDistrictName
    }

}