package com.redveloper.rebator.utils

import android.Manifest
import android.app.Activity
import android.os.Build
import android.widget.Toast
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener

fun Activity.askPermission(vararg permission:String, granted:() -> Unit){
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
        granted.invoke()
        return
    }
    Dexter.withContext(this)
        .withPermissions(permission.toMutableList())
        .withListener(object : MultiplePermissionsListener{
            override fun onPermissionsChecked(p0: MultiplePermissionsReport?) {
                if (p0?.areAllPermissionsGranted() == true){
                    granted.invoke()
                } else {
                    val deniedPermission = p0?.deniedPermissionResponses?.get(0)?.permissionName
                    when(deniedPermission){
                        Manifest.permission.CAMERA -> {
                            Toast.makeText(this@askPermission, "tidak mendapatkan permission camera", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

            override fun onPermissionRationaleShouldBeShown(
                p0: MutableList<PermissionRequest>?,
                p1: PermissionToken?
            ) {
                p1?.continuePermissionRequest()
            }
        }).check()
}