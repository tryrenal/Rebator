package com.redveloper.rebator.utils

import android.content.Context
import android.view.View
import android.widget.Toast

fun View.gone(){
    this.visibility = View.GONE
}

fun View.visible(){
    this.visibility = View.VISIBLE
}

fun View.invisible(){
    this.visibility = View.INVISIBLE
}

fun View.setVisility(show: Boolean){
    if (show){
        this.visible()
    } else{
        this.gone()
    }
}

fun Context.toast(message: String){
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}