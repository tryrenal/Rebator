package com.redveloper.akusisi.ui.editprofile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.redveloper.akusisi.databinding.FragmentEditProfileBinding
import com.redveloper.akusisi.di.Inject
import com.redveloper.akusisi.ui.AkusisiActivity
import com.redveloper.akusisi.ui.editprofile.model.EditProfileModel
import com.redveloper.rebator.domain.entity.Position
import com.redveloper.rebator.domain.entity.User
import com.redveloper.rebator.ui.BaseFragment
import com.redveloper.rebator.utils.image.load
import com.redveloper.rebator.utils.setVisility
import com.redveloper.rebator.utils.toast
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditProfileFragment : BaseFragment<FragmentEditProfileBinding>() {

    val editViewModel: EditProfileViewModel by viewModel()

    fun inject() = Inject.loadKoinModules

    override fun inflate(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentEditProfileBinding {
        return FragmentEditProfileBinding.inflate(inflater, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inject()
        initView()
        initClicklistener()
        initObserver()
    }

    private fun initView(){
        editViewModel.getUser()
    }

    private fun initObserver(){
        observerError()

        editViewModel.loadingEvent.observe(viewLifecycleOwner){
            it.contentIfNotHaveBeenHandle?.let {
                binding.progressBar.setVisility(it)
            }
        }

        editViewModel.getUserEvent.observe(viewLifecycleOwner){
            it.contentIfNotHaveBeenHandle?.let {
                initialUserData(it)
            }
        }

        editViewModel.successSubmitEvent.observe(viewLifecycleOwner){
            it.contentIfNotHaveBeenHandle?.let { done ->
                if (done){
                    findNavController().navigateUp()
                }
            }
        }
    }

    private fun observerError(){
        editViewModel.errorNameEvent.observe(viewLifecycleOwner){
            it.contentIfNotHaveBeenHandle?.let {
                binding.edtName.error = it
            }
        }

        editViewModel.errorPosition.observe(viewLifecycleOwner){
            it.contentIfNotHaveBeenHandle?.let {
                binding.edtPosition.error = it
            }
        }

        editViewModel.errorPhotoEvent.observe(viewLifecycleOwner){
            it.contentIfNotHaveBeenHandle?.let {
                requireActivity().toast(it)
            }
        }

        editViewModel.errorPhoneNumberEvent.observe(viewLifecycleOwner){
            it.contentIfNotHaveBeenHandle?.let {
                binding.edtPhoneNumber.error = it
            }
        }

        editViewModel.errorGetUserEvent.observe(viewLifecycleOwner){
            it.contentIfNotHaveBeenHandle?.let {
                requireActivity().toast(it)
            }
        }

        editViewModel.errorSubmitEvent.observe(viewLifecycleOwner){
            it.contentIfNotHaveBeenHandle?.let {
                requireActivity().toast(it)
            }
        }
    }

    private fun initialUserData(data: User){
        binding.imgUser.load(data.photoUrl)
        editViewModel.photoUri = data.photoUrl
        binding.edtEmail.setText(data.email)
        binding.edtName.setText(data.name)
        binding.edtPosition.setText(data.position.name)
        binding.edtPhoneNumber.setText(data.phoneNumber)
    }

    private fun initClicklistener(){
        binding.btnSave.setOnClickListener {
            submitData()
        }
        binding.layoutAppbar.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun submitData(){
        val data = EditProfileModel(
            name = binding.edtName.text.toString(),
            photoUri = editViewModel.photoUri,
            phoneNumber = binding.edtPhoneNumber.text.toString(),
            position = Position.valueOf(binding.edtPosition.text.toString())
        )

        editViewModel.submit(data)
    }

}