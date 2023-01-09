package com.redveloper.akusisi.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.redveloper.akusisi.R
import com.redveloper.akusisi.databinding.ActivityAkusisiBinding
import com.redveloper.akusisi.ui.dashboard.DashboardFragment
import com.redveloper.akusisi.ui.profile.ProfileFragment
import com.redveloper.rebator.ui.BaseActivity

class AkusisiActivity : BaseActivity<ActivityAkusisiBinding>() {

    private lateinit var dashboardFragment: DashboardFragment
    private lateinit var profileFragment: ProfileFragment

    override fun inflate(): ActivityAkusisiBinding {
        return ActivityAkusisiBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dashboardFragment = DashboardFragment()
        profileFragment = ProfileFragment()

        //initial menu
        setCurrentFragment(dashboardFragment)

        binding.bottomNavigationView.setOnItemSelectedListener {
            val id = it.itemId
            when(id){
                R.id.menu_home -> setCurrentFragment(dashboardFragment)
                R.id.menu_profile -> setCurrentFragment(profileFragment)
            }
            true
        }
    }

    private fun setCurrentFragment(fragment: Fragment) =
        run {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.framelayout, fragment)
                commit()
            }
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