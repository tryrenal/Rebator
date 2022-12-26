package com.redveloper.rebator.ui.register.model

import com.redveloper.rebator.domain.entity.Position

data class RegisterModel(
    val email: String?,
    val password: String?,
    val name: String?,
    val photo: String?,
    val posisi: Position?,
    val phoneNumber: String?
)