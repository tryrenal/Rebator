package com.redveloper.rebator.router

import android.app.Activity
import android.content.Intent

class AkusisiRouter {
    companion object{
        private const val KEY_PAGE = "com.redveloper.akusisi.ui.AkusisiActivity"

        fun navigate(activity: Activity, finish: Boolean = false){
            val intent = Intent(activity, Class.forName(KEY_PAGE))
            activity.startActivity(intent)
            if (finish){
                activity.finish()
            }
        }
    }
}