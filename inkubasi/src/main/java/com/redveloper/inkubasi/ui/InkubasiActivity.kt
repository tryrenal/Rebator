package com.redveloper.inkubasi.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.redveloper.inkubasi.R
import com.redveloper.inkubasi.databinding.ActivityInkubasiBinding
import com.redveloper.inkubasi.ui.dashboard.DashboardInkubasiFragment
import com.redveloper.inkubasi.ui.profile.ProfileFragment
import com.redveloper.rebator.ui.BaseActivity

class InkubasiActivity : BaseActivity<ActivityInkubasiBinding>() {

    private lateinit var navController: NavController

    private lateinit var dashboardInkubasiFragment: DashboardInkubasiFragment
    private lateinit var profileFragment: ProfileFragment

    override fun inflate(): ActivityInkubasiBinding {
        return ActivityInkubasiBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dashboardInkubasiFragment = DashboardInkubasiFragment()
        profileFragment = ProfileFragment()

        navController = Navigation.findNavController(this, R.id.framelayout)
        binding.bottomNavigationView.setupWithNavController(navController)

    }

    fun setBottomNavigationVisibiility(visibility: Int){
        binding.bottomNavigationView.visibility = visibility
    }

    companion object{
        fun navigate(activity: Activity, finish: Boolean = false){
            val intent = Intent(activity, InkubasiActivity::class.java)
            activity.startActivity(intent)
            if (finish){
                activity.finish()
            }
        }
    }
}