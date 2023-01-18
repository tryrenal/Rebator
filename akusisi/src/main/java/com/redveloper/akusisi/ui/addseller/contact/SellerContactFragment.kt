package com.redveloper.akusisi.ui.addseller.contact

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.redveloper.akusisi.databinding.FragmentSellerContactBinding
import com.redveloper.akusisi.ui.AkusisiActivity
import com.redveloper.akusisi.ui.AkusisiBaseFragment
import com.redveloper.akusisi.ui.addseller.model.AddSellerModel
import com.redveloper.rebator.R
import com.redveloper.rebator.design.popup.SingleSelectedPopUp
import com.redveloper.rebator.domain.entity.Gender
import com.redveloper.rebator.domain.entity.Position
import com.redveloper.rebator.utils.mapper.GenderMapper
import com.redveloper.rebator.utils.setVisility
import com.redveloper.rebator.utils.toast
import org.koin.androidx.viewmodel.ext.android.viewModel

class SellerContactFragment : AkusisiBaseFragment<FragmentSellerContactBinding>() {

    val sellerViewModel: SellerContactViewModel by viewModel()
    val args by navArgs<SellerContactFragmentArgs>()

    lateinit var addSellerModel: AddSellerModel

    override fun inflate(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSellerContactBinding {
        return FragmentSellerContactBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initClicklistener()
        initObserver()
    }

    private fun initView(){
        args.addSellerModel?.let {
            addSellerModel = it
        }
    }

    private fun initClicklistener(){
        binding.layoutAppbar.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.btnSave.setOnClickListener {
            addSellerModel.sellerName = binding.edtName.text.toString()
            addSellerModel.sellerPhoneNumber = binding.edtPhoneNumber.text.toString()

            sellerViewModel.submit(addSellerModel)
        }

        binding.edtGender.setOnClickListener {
            showPopUpGender()
        }
    }

    private fun showPopUpGender(){
        val listData = arrayListOf(
            Pair(Gender.MAN.name, GenderMapper.getValueOfGender(Gender.MAN)),
            Pair(Gender.WOMAN.name, GenderMapper.getValueOfGender(Gender.WOMAN)),
        )
        val singlePopUp = SingleSelectedPopUp.create(resources.getString(R.string.gender), listData)
        singlePopUp.safeShow(childFragmentManager, "single pop up")
        singlePopUp.listener = object : SingleSelectedPopUp.Listener{
            override fun itemChoose(item: Pair<String, String>) {
                sellerViewModel.genderSelected = item
                binding.edtGender.setText(item.second)
            }
        }
    }

    private fun initObserver(){
        observerError()

        sellerViewModel.loadingEvent.observe(viewLifecycleOwner){
            it.contentIfNotHaveBeenHandle?.let {
                binding.progressBar.setVisility(it)
            }
        }

        sellerViewModel.successSubmitEvent.observe(viewLifecycleOwner){
            it.contentIfNotHaveBeenHandle?.let {
                AkusisiActivity.navigate(requireActivity(), true)
            }
        }
    }

    private fun observerError(){
        sellerViewModel.errorNameEvent.observe(viewLifecycleOwner){
            it.contentIfNotHaveBeenHandle?.let {
                binding.edtName.error = it
            }
        }

        sellerViewModel.errorPhoneNumberEvent.observe(viewLifecycleOwner){
            it.contentIfNotHaveBeenHandle?.let {
                binding.edtPhoneNumber.error = it
            }
        }

        sellerViewModel.errorGenderEvent.observe(viewLifecycleOwner){
            it.contentIfNotHaveBeenHandle?.let {
                binding.edtGender.error = it
            }
        }

        sellerViewModel.errorSubmitEvent.observe(viewLifecycleOwner){
            it.contentIfNotHaveBeenHandle?.let {
                requireActivity().toast(it)
            }
        }
    }


}