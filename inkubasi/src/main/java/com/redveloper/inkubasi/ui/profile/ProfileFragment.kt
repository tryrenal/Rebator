package com.redveloper.inkubasi.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.redveloper.inkubasi.databinding.FragmentProfileBinding
import com.redveloper.inkubasi.ui.InkubasiActivity
import com.redveloper.inkubasi.ui.InkubasiBaseFragment
import com.redveloper.inkubasi.ui.profile.model.MenuProfile
import com.redveloper.inkubasi.ui.profile.model.MenuProfileEnum
import com.redveloper.rebator.ui.login.LoginActivity
import com.redveloper.rebator.utils.image.load
import com.redveloper.rebator.utils.setVisility
import com.redveloper.rebator.utils.toast
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.redveloper.inkubasi.R as InkubasiR
import com.redveloper.rebator.R as AppR


class ProfileFragment : InkubasiBaseFragment<FragmentProfileBinding>() {

    private lateinit var menuAdapter: ProfileAdapter
    val profileViewModel: ProfileViewModel by viewModel()

    override var bottomNavigationVisibility: Int = View.VISIBLE

    override fun inflate(inflater: LayoutInflater, container: ViewGroup?): FragmentProfileBinding {
        return FragmentProfileBinding.inflate(inflater, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initClicklistener()
        initObserver()
    }

    private fun initView(){
        menuAdapter = ProfileAdapter()

        profileViewModel.getUser()

        ContextCompat.getDrawable(requireContext(), AppR.drawable.ic_pencil_edit)?.let {
            binding.layoutNameInkubasi.icArrow.setImageDrawable(it)
        }

        setRecyclerMenu()
    }

    private fun setRecyclerMenu(){
        val listMenu = listOf(
            MenuProfile(menu = MenuProfileEnum.EDIT_DATA_USER, text = resources.getString(InkubasiR.string.edit_data_user), image = AppR.drawable.ic_setting),
            MenuProfile(menu = MenuProfileEnum.ABOUT_US, text = resources.getString(InkubasiR.string.about_us), image = AppR.drawable.ic_book),
            MenuProfile(menu = MenuProfileEnum.LOGOUT, text = resources.getString(InkubasiR.string.logout), image = AppR.drawable.ic_logout),
        )
        menuAdapter.submitList(listMenu)
        binding.recyclerMenu.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = menuAdapter
        }
    }

    private fun initClicklistener(){
        menuAdapter.menuSelected = { menu ->
            when(menu){
                MenuProfileEnum.EDIT_DATA_USER -> {
                    (activity as InkubasiActivity).setBottomNavigationVisibiility(View.GONE)
                    findNavController().navigate(InkubasiR.id.action_to_editprofile)
                }
                MenuProfileEnum.ABOUT_US -> {
                    (activity as InkubasiActivity).setBottomNavigationVisibiility(View.GONE)
                    findNavController().navigate(InkubasiR.id.action_to_aboutus)
                }
                MenuProfileEnum.LOGOUT -> {
                    profileViewModel.logout()
                }
            }
        }
    }

    private fun initObserver(){
        profileViewModel.loadingEvent.observe(viewLifecycleOwner){
            it.contentIfNotHaveBeenHandle?.let {
                binding.progressBar.setVisility(it)
            }
        }

        profileViewModel.errorGetUserEvent.observe(viewLifecycleOwner){
            it.contentIfNotHaveBeenHandle?.let {
                requireActivity().toast(it)
            }
        }

        profileViewModel.errorLogoutEvent.observe(viewLifecycleOwner){
            it.contentIfNotHaveBeenHandle?.let {
                requireActivity().toast(it)
            }
        }

        profileViewModel.userEvent.observe(viewLifecycleOwner){
            it.contentIfNotHaveBeenHandle?.let {
                binding.imgUser.load(it.photoUrl)
                binding.layoutNameInkubasi.tvMenu.text = it.name
            }
        }

        profileViewModel.logoutUserEvent.observe(viewLifecycleOwner){
            it.contentIfNotHaveBeenHandle?.let {
                LoginActivity.navigate(activity = requireActivity(), finish = true)
            }
        }
    }
}