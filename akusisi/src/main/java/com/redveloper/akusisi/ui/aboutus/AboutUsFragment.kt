package com.redveloper.akusisi.ui.aboutus

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.redveloper.akusisi.R
import com.redveloper.akusisi.databinding.FragmentAboutUsBinding
import com.redveloper.akusisi.ui.AkusisiBaseFragment

class AboutUsFragment : AkusisiBaseFragment<FragmentAboutUsBinding>() {

    override fun inflate(inflater: LayoutInflater, container: ViewGroup?): FragmentAboutUsBinding {
        return FragmentAboutUsBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvDescAboutUs.text = Html.fromHtml(resources.getString(R.string.desc_about_us))

        binding.layoutAppbar.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

}