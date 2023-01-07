package com.redveloper.rebator.ui.register.camerauser

import android.Manifest
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.redveloper.rebator.R
import com.redveloper.rebator.databinding.FragmentRegisterCameraUserBinding
import com.redveloper.rebator.ui.BaseFragment
import com.redveloper.rebator.ui.camerax.CameraActivity
import com.redveloper.rebator.utils.askPermission
import com.redveloper.rebator.utils.image.rotateBitmap
import com.redveloper.rebator.utils.setVisility
import com.redveloper.rebator.utils.toast
import id.zelory.compressor.Compressor
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File
import java.io.FileInputStream
import java.nio.file.Files

class RegisterCameraUserFragment : BaseFragment<FragmentRegisterCameraUserBinding>() {

    val regisViewModel: RegisterCameraUserViewModel by viewModel()
    private var fileTemp: File? = null

    override fun inflate(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentRegisterCameraUserBinding {
        return FragmentRegisterCameraUserBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initClicklistener()
        initObserver()
    }

    private fun initView(){

    }

    private fun initClicklistener(){
        binding.imgPhotoProfile.setOnClickListener {
            activity?.askPermission(Manifest.permission.CAMERA){
                CameraActivity.navigate(requireActivity(), CAMERA_RESULT, launchCameraX)
            }
        }
        binding.appbar.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.btnSave.setOnClickListener {
            regisViewModel.submit(fileTemp)
        }
    }

    private fun initObserver(){
        errorObserver()
        regisViewModel.loadingEvent.observe(viewLifecycleOwner){
            it.contentIfNotHaveBeenHandle?.let {
                binding.progressBar.setVisility(it)
            }
        }

        regisViewModel.successEvent.observe(viewLifecycleOwner){
            it.contentIfNotHaveBeenHandle?.let {
                findNavController().navigate(R.id.action_to_registerInformasiUserFragment)
            }
        }
    }

    private fun errorObserver(){
        regisViewModel.errorPhotoEvent.observe(viewLifecycleOwner){
            it.contentIfNotHaveBeenHandle?.let {
                requireActivity().toast(it)
            }
        }

        regisViewModel.errorEvent.observe(viewLifecycleOwner){
            it.contentIfNotHaveBeenHandle?.let {
                requireActivity().toast(it)
            }
        }
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
                    binding.imgPhotoProfile.setImageBitmap(result)
                }
            }
        }
    }

    companion object{
        private const val CAMERA_RESULT = 123
    }
}