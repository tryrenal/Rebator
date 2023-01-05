package com.redveloper.rebator.ui.register.informasiuser

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.redveloper.rebator.MainActivity
import com.redveloper.rebator.databinding.FragmentRegisterInformasiUserBinding
import com.redveloper.rebator.domain.entity.Position
import com.redveloper.rebator.ui.BaseFragment
import com.redveloper.rebator.ui.register.informasiuser.model.RegisterInformasiUserModel
import com.redveloper.rebator.utils.State
import com.redveloper.rebator.utils.gone
import com.redveloper.rebator.utils.setVisility
import com.redveloper.rebator.utils.visible
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterInformasiUserFragment : BaseFragment<FragmentRegisterInformasiUserBinding>() {

    val regisViewModel: RegisterInformasiUserViewModel by viewModel()

    override fun inflate(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentRegisterInformasiUserBinding {
        return FragmentRegisterInformasiUserBinding.inflate(inflater, container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initClicklistener()
        initObserver()
    }

    private fun initView(){
        spinnerPosition()
    }

    private fun spinnerPosition(){
        val data = listOf(Position.AKUSISI.name, Position.INKUBASI.name)
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, data)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerPosition.adapter = adapter
        binding.spinnerPosition.onItemSelectedListener = object  : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                regisViewModel.positionSelected = Position.valueOf(data[p2])
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                regisViewModel.positionSelected = null
            }
        }
    }

    private fun initClicklistener(){
        binding.btnSubmit.setOnClickListener {
            val data = RegisterInformasiUserModel(
                name = binding.edtName.text.toString(),
                phoneNumber = binding.edtPhonenumber.text.toString(),
                position = regisViewModel.positionSelected
            )
            regisViewModel.submit(data)
        }
    }

    private fun initObserver(){
        errorObserver()

        regisViewModel.loadingEvent.observe(viewLifecycleOwner){
            it.contentIfNotHaveBeenHandle?.let {
                binding.progressBar.setVisility(it)
            }
        }

        regisViewModel.successSubmitEvent.observe(viewLifecycleOwner){
            it.contentIfNotHaveBeenHandle?.let {
                Toast.makeText(requireContext(), "success", Toast.LENGTH_SHORT).show()
                activity?.startActivity(Intent(requireContext(), MainActivity::class.java))
            }
        }
    }

    private fun errorObserver(){
        regisViewModel.errorSubmitEvent.observe(viewLifecycleOwner){
            it.contentIfNotHaveBeenHandle?.let {
                Toast.makeText(requireContext(), "error: $it", Toast.LENGTH_SHORT).show()
            }
        }
        regisViewModel.errorNameEvent.observe(viewLifecycleOwner){
            it.contentIfNotHaveBeenHandle?.let {
                Toast.makeText(requireContext(), "error name: $it", Toast.LENGTH_SHORT).show()
            }
        }

        regisViewModel.errorPhoneNumberEvent.observe(viewLifecycleOwner){
            it.contentIfNotHaveBeenHandle?.let {
                Toast.makeText(requireContext(), "error phonenumber: $it", Toast.LENGTH_SHORT).show()
            }
        }

        regisViewModel.errorPositionEvent.observe(viewLifecycleOwner){
            it.contentIfNotHaveBeenHandle?.let {
                Toast.makeText(requireContext(), "error position: $it", Toast.LENGTH_SHORT).show()
            }
        }
    }

}