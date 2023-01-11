package com.redveloper.akusisi.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.redveloper.akusisi.R
import com.redveloper.akusisi.databinding.ActivityAkusisiBinding
import com.redveloper.akusisi.ui.dashboard.DashboardFragment
import com.redveloper.akusisi.ui.profile.ProfileFragment
import com.redveloper.rebator.ui.BaseActivity
import com.redveloper.rebator.utils.setVisility

class AkusisiActivity : BaseActivity<ActivityAkusisiBinding>() {

    private lateinit var navController: NavController

    private lateinit var dashboardFragment: DashboardFragment
    private lateinit var profileFragment: ProfileFragment

    override fun inflate(): ActivityAkusisiBinding {
        return ActivityAkusisiBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dashboardFragment = DashboardFragment()
        profileFragment = ProfileFragment()

        navController = Navigation.findNavController(this, R.id.framelayout)
        binding.bottomNavigationView.setupWithNavController(navController)
    }

    fun setBottomNavigationVisibility(visibility: Int){
        binding.bottomNavigationView.visibility = visibility
    }

    companion object{
        fun navigate(activity: Activity, finish: Boolean = false){
            val intent = Intent(activity, AkusisiActivity::class.java)
            activity.startActivity(intent)
            if (finish){
                activity.finish()
            }
        }
    }
}