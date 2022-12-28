package com.redveloper.rebator.ui.register

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.redveloper.rebator.databinding.ActivityRegisterBinding
import com.redveloper.rebator.ui.BaseActivity

class RegisterActivity : BaseActivity<ActivityRegisterBinding>() {

    override fun inflate(): ActivityRegisterBinding {
        return ActivityRegisterBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    companion object{
        fun navigate(activity: Activity){
            val intent = Intent(activity, RegisterActivity::class.java)
            activity.startActivity(intent)
        }
    }
}