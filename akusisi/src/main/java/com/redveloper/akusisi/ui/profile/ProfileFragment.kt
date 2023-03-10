package com.redveloper.akusisi.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.menu.MenuAdapter
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.redveloper.akusisi.databinding.FragmentProfileBinding
import com.redveloper.akusisi.di.Inject
import com.redveloper.akusisi.ui.AkusisiActivity
import com.redveloper.akusisi.ui.AkusisiBaseFragment
import com.redveloper.akusisi.ui.profile.model.MenuProfile
import com.redveloper.akusisi.ui.profile.model.MenuProfileEnum
import com.redveloper.rebator.ui.BaseFragment
import com.redveloper.rebator.ui.login.LoginActivity
import com.redveloper.rebator.utils.image.load
import com.redveloper.rebator.utils.setVisility
import com.redveloper.rebator.utils.toast
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.redveloper.rebator.R as AppR
import com.redveloper.akusisi.R as AkusisiR

class ProfileFragment : AkusisiBaseFragment<FragmentProfileBinding>() {

    val profileViewModel: ProfileViewModel by viewModel()

    override var bottomNavigationVisibility: Int = View.VISIBLE

    private lateinit var menuAdapter: AdapterMenuProfile

    override fun inflate(inflater: LayoutInflater, container: ViewGroup?): FragmentProfileBinding {
        return FragmentProfileBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inject()
        initView()
        initObserver()
        initClicklistener()
    }

    private fun initView(){
        menuAdapter = AdapterMenuProfile()

        profileViewModel.getUser()
        ContextCompat.getDrawable(requireContext(), AppR.drawable.ic_pencil_edit)
            ?.let { binding.layoutName.icArrow.load(it) }

        setRecyclerMenu()
    }

    private fun setRecyclerMenu(){
        val listMenu = listOf(
            MenuProfile(menu = MenuProfileEnum.EDIT_DATA_USER, text = resources.getString(AkusisiR.string.edit_data_user), image = AppR.drawable.ic_setting),
            MenuProfile(menu = MenuProfileEnum.ABOUT_US, text = resources.getString(AkusisiR.string.about_us), image = AppR.drawable.ic_book),
            MenuProfile(menu = MenuProfileEnum.LOG_OUT, text = resources.getString(AkusisiR.string.logout), image = AppR.drawable.ic_logout),
        )
        menuAdapter.submitList(listMenu)
        binding.recyclerMenu.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = menuAdapter
        }
    }

    private fun initObserver(){
        profileViewModel.loadingEvent.observe(viewLifecycleOwner){
            it.contentIfNotHaveBeenHandle?.let {
                binding.progressBar.setVisility(it)
            }
        }

        profileViewModel.userEvent.observe(viewLifecycleOwner){
            it.contentIfNotHaveBeenHandle?.let {
                binding.imgUser.load(it.photoUrl)
                binding.layoutName.tvMenu.text = it.name
            }
        }

        profileViewModel.errorGetUserEvent.observe(viewLifecycleOwner){
            it.contentIfNotHaveBeenHandle?.let {
                requireActivity().toast(it)
            }
        }

        profileViewModel.logoutUserEvent.observe(viewLifecycleOwner){
            it.contentIfNotHaveBeenHandle?.let { done ->
                if (done){
                    LoginActivity.navigate(activity = requireActivity(), finish = true)
                }
            }
        }

        profileViewModel.errorLogoutEvent.observe(viewLifecycleOwner){
            it.contentIfNotHaveBeenHandle?.let {
                requireActivity().toast(it)
            }
        }
    }

    private fun initClicklistener(){
        menuAdapter.menuSelected = { menu ->
            when(menu){
                MenuProfileEnum.EDIT_DATA_USER -> {
                    (activity as AkusisiActivity).setBottomNavigationVisibility(View.GONE)
                    findNavController().navigate(AkusisiR.id.action_to_edit_profile)
                }
                MenuProfileEnum.ABOUT_US -> {
                    (activity as AkusisiActivity).setBottomNavigationVisibility(View.GONE)
                    findNavController().navigate(AkusisiR.id.action_to_aboutUs)
                }
                MenuProfileEnum.LOG_OUT -> {
                    profileViewModel.logout()
                }
            }
        }
    }

}