package com.redveloper.rebator

import android.os.Bundle
import com.redveloper.rebator.databinding.ActivityMainBinding
import com.redveloper.rebator.ui.BaseActivity

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun inflate(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
}