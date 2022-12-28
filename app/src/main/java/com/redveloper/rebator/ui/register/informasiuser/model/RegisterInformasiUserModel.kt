package com.redveloper.rebator.ui.register.informasiuser.model

import com.redveloper.rebator.domain.entity.Position

data class RegisterInformasiUserModel(
    val name: String?,
    val phoneNumber: String?,
    val position: Position?
)