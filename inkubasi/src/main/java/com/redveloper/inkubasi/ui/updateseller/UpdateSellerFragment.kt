package com.redveloper.inkubasi.ui.updateseller

import android.Manifest
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.redveloper.inkubasi.R as InkubasiR
import com.redveloper.rebator.R as AppR
import com.redveloper.inkubasi.databinding.FragmentUpdateSellerBinding
import com.redveloper.inkubasi.ui.InkubasiActivity
import com.redveloper.inkubasi.ui.InkubasiBaseFragment
import com.redveloper.rebator.design.popup.SingleSelectedPopUp
import com.redveloper.rebator.ui.camerax.CameraActivity
import com.redveloper.rebator.utils.askPermission
import com.redveloper.rebator.utils.date.DateUtils
import com.redveloper.rebator.utils.image.rotateBitmap
import com.redveloper.rebator.utils.setVisility
import com.redveloper.rebator.utils.toast
import id.zelory.compressor.Compressor
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File

class UpdateSellerFragment : InkubasiBaseFragment<FragmentUpdateSellerBinding>() {

    val updateViewModel: UpdateSellerViewModel by viewModel()
    val args by navArgs<UpdateSellerFragmentArgs>()

    private var fileTemp: File? = null

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
        updateViewModel.getUser()
        binding.tvTiktokId.text = args.tiktokid
    }

    private fun initClicklistener(){
        binding.imgSeller.setOnClickListener {
            activity?.askPermission(Manifest.permission.CAMERA){
                CameraActivity.navigate(requireActivity(), CAMERA_RESULT, launchCameraX)
            }
        }
        binding.edtPotential.setOnClickListener {
            updateViewModel.getListPotential()
        }
        binding.edtVisit.setOnClickListener {
            updateViewModel.getListResultVisit()
        }
        binding.edtStatus.setOnClickListener {
            updateViewModel.getListStatus()
        }
        binding.btnSave.setOnClickListener {
            submit()
        }
    }

    private fun submit(){
        val date = DateUtils.getCurrentTimestamp()
        updateViewModel.submit(
            tiktokId = binding.tvTiktokId.text.toString(),
            inkubasiName = binding.edtName.text.toString(),
            inkubasiDate = date,
            note = binding.edtNote.text.toString(),
            photo = fileTemp
        )
    }

    private fun initObserver(){
        observerError()
        updateViewModel.loadingEvent.observe(viewLifecycleOwner){
            it.contentIfNotHaveBeenHandle?.let {
                binding.progressBar.setVisility(it)
            }
        }
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
        updateViewModel.userEvent.observe(viewLifecycleOwner){
            it.contentIfNotHaveBeenHandle?.let {
                binding.edtName.setText(it.name)
            }
        }
        updateViewModel.submitSuccessEvent.observe(viewLifecycleOwner){
            it.contentIfNotHaveBeenHandle?.let {
                InkubasiActivity.navigate(activity = requireActivity(), finish = true)
            }
        }
    }

    private fun observerError(){
        updateViewModel.errorMessageEvent.observe(viewLifecycleOwner){
            it.contentIfNotHaveBeenHandle?.let {
                requireActivity().toast(it)
            }
        }
        updateViewModel.errorPotentialEvent.observe(viewLifecycleOwner){
            it.contentIfNotHaveBeenHandle?.let {
                binding.edtPotential.error = it
            }
        }
        updateViewModel.errorStatusEvent.observe(viewLifecycleOwner){
            it.contentIfNotHaveBeenHandle?.let {
                binding.edtStatus.error = it
            }
        }
        updateViewModel.errorVisitEvent.observe(viewLifecycleOwner){
            it.contentIfNotHaveBeenHandle?.let {
                binding.edtVisit.error = it
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
                    binding.imgSeller.setImageBitmap(result)
                }
            }
        }
    }

    companion object{
        private const val CAMERA_RESULT = 123
    }

}