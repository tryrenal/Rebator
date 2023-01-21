package com.redveloper.inkubasi.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.redveloper.inkubasi.di.Inject

abstract class InkubasiBaseFragment <T: ViewBinding>: Fragment(){
    private var _binding: T? = null
    protected val binding get() = _binding!!

    protected open var bottomNavigationVisibility = View.GONE

    fun inject() = Inject.loadKoinModules

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflate(inflater, container)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inject()
    }

    override fun onResume() {
        super.onResume()
        if (activity is InkubasiActivity){
            (activity as InkubasiActivity).setBottomNavigationVisibiility(bottomNavigationVisibility)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    abstract fun inflate(inflater: LayoutInflater, container: ViewGroup?): T
}