package com.redveloper.akusisi.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.redveloper.akusisi.R
import com.redveloper.akusisi.databinding.FragmentProfileBinding
import com.redveloper.akusisi.di.Inject
import com.redveloper.akusisi.di.viewModelModule
import com.redveloper.rebator.ui.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class ProfileFragment : BaseFragment<FragmentProfileBinding>() {

    val profileViewModel: ProfileViewModel by viewModel()

    fun inject() = Inject.loadKoinModules

    override fun inflate(inflater: LayoutInflater, container: ViewGroup?): FragmentProfileBinding {
        return FragmentProfileBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inject()

        profileViewModel.setText("hello renal")

        profileViewModel.textEvent.observe(viewLifecycleOwner){
            it.contentIfNotHaveBeenHandle?.let {
                binding.tvText.text = it
            }
        }
    }

}