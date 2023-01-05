package com.redveloper.rebator.utils

import android.view.View

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