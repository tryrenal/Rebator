package com.redveloper.rebator.ui.register.camerauser

import android.Manifest
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.redveloper.rebator.R
import com.redveloper.rebator.databinding.FragmentRegisterCameraUserBinding
import com.redveloper.rebator.ui.BaseFragment
import com.redveloper.rebator.ui.camerax.CameraActivity
import com.redveloper.rebator.utils.askPermission
import com.redveloper.rebator.utils.image.rotateBitmap
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File

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
        binding.imgPhoto.setOnClickListener {
            activity?.askPermission(Manifest.permission.CAMERA){
                CameraActivity.navigate(requireActivity(), CAMERA_RESULT, launchCameraX)
            }
        }
        binding.btnCancel.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.btnSubmit.setOnClickListener {
            findNavController().navigate(R.id.action_to_registerInformasiUserFragment)
        }
    }

    private fun initObserver(){
        errorObserver()
    }

    private fun errorObserver(){
        regisViewModel.errorPhotoEvent.observe(viewLifecycleOwner){
            it.contentIfNotHaveBeenHandle?.let {
                Toast.makeText(requireContext(), "error photo: $it", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private val launchCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){
        if (it.resultCode == CAMERA_RESULT){
            fileTemp = it.data?.getSerializableExtra("picture") as File
            val isBackCamera = it.data?.getBooleanExtra("isBackCamera", true) as Boolean

            fileTemp?.let {
                val result = rotateBitmap(
                    BitmapFactory.decodeFile(it.path),
                    isBackCamera
                )

                binding.imgPhoto.setImageBitmap(result)
            }
        }
    }

    companion object{
        private const val CAMERA_RESULT = 123
    }
}