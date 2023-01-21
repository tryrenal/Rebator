package com.redveloper.inkubasi.ui.profile

import android.opengl.Visibility
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.redveloper.inkubasi.R as InkubasiR
import com.redveloper.rebator.R as AppR
import com.redveloper.inkubasi.databinding.FragmentProfileBinding
import com.redveloper.inkubasi.ui.InkubasiActivity
import com.redveloper.inkubasi.ui.InkubasiBaseFragment
import com.redveloper.inkubasi.ui.profile.model.MenuProfile
import com.redveloper.inkubasi.ui.profile.model.MenuProfileEnum
import com.redveloper.rebator.utils.image.load


class ProfileFragment : InkubasiBaseFragment<FragmentProfileBinding>() {

    private lateinit var menuAdapter: ProfileAdapter

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

                }
                MenuProfileEnum.ABOUT_US -> {
                    (activity as InkubasiActivity).setBottomNavigationVisibiility(View.GONE)
                    findNavController().navigate(InkubasiR.id.action_to_aboutus)
                }
                MenuProfileEnum.LOGOUT -> {

                }
            }
        }
    }

    private fun initObserver(){

    }
}