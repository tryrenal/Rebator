package com.redveloper.rebator.utils.image

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.redveloper.rebator.R
import java.io.File

fun ImageView.load(file: File){
    Glide.with(context)
        .asBitmap()
        .load(file)
        .centerInside()
        .placeholder(R.drawable.ic_image)
        .into(this)
}

fun ImageView.load(url: String){
    Glide.with(context)
        .asBitmap()
        .load(url)
        .centerInside()
        .placeholder(R.drawable.ic_image)
        .into(this)
}

fun ImageView.load(drawable: Drawable){
    Glide.with(context)
        .asBitmap()
        .load(drawable)
        .into(this)
}