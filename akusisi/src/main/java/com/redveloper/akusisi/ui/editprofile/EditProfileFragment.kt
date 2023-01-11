package com.redveloper.akusisi.ui.editprofile

import android.Manifest
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.redveloper.akusisi.databinding.FragmentEditProfileBinding
import com.redveloper.akusisi.di.Inject
import com.redveloper.akusisi.ui.AkusisiActivity
import com.redveloper.akusisi.ui.AkusisiBaseFragment
import com.redveloper.akusisi.ui.editprofile.model.EditProfileModel
import com.redveloper.rebator.R
import com.redveloper.rebator.design.popup.SingleSelectedPopUp
import com.redveloper.rebator.domain.entity.Position
import com.redveloper.rebator.domain.entity.User
import com.redveloper.rebator.ui.BaseFragment
import com.redveloper.rebator.ui.camerax.CameraActivity
import com.redveloper.rebator.utils.askPermission
import com.redveloper.rebator.utils.image.load
import com.redveloper.rebator.utils.image.rotateBitmap
import com.redveloper.rebator.utils.mapper.PositionMapper
import com.redveloper.rebator.utils.setVisility
import com.redveloper.rebator.utils.toast
import id.zelory.compressor.Compressor
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File

class EditProfileFragment : AkusisiBaseFragment<FragmentEditProfileBinding>() {

    val editViewModel: EditProfileViewModel by viewModel()

    private var fileTemp: File? = null

    override fun inflate(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentEditProfileBinding {
        return FragmentEditProfileBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
        binding.edtPosition.setText(PositionMapper.getValueOfPosition(data.position))
        binding.edtPhoneNumber.setText(data.phoneNumber)
    }

    private fun initClicklistener(){
        binding.btnSave.setOnClickListener {
            submitData()
        }
        binding.layoutAppbar.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.imgUser.setOnClickListener {
            activity?.askPermission(Manifest.permission.CAMERA){
                CameraActivity.navigate(requireActivity(), CAMERA_RESULT, launchCameraX)
            }
        }
    }

    private fun submitData(){
        val data = EditProfileModel(
            name = binding.edtName.text.toString(),
            photoUri = editViewModel.photoUri,
            phoneNumber = binding.edtPhoneNumber.text.toString(),
            position = PositionMapper.getPositionByValue(binding.edtPosition.text.toString())
        )

        editViewModel.submit(data)
    }

    private val launchCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){
        if (it.resultCode == CAMERA_RESULT){
            lifecycleScope.launch {
                val file = it.data?.getSerializableExtra("picture") as File
                val isBackCamera = it.data?.getBooleanExtra("isBackCamera", true) as Boolean
                fileTemp = Compressor.compress(requireContext(), file)
                fileTemp?.let {
                    val result = rotateBitmap(BitmapFactory.decodeFile(it.path), isBackCamera)
                    binding.imgUser.setImageBitmap(result)
                    editViewModel.photoUri = Uri.fromFile(it).toString()
                }
            }
        }
    }

    companion object{
        private const val CAMERA_RESULT = 234
    }

}