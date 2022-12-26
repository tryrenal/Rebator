package com.redveloper.rebator.data.network.auth.model

import com.redveloper.rebator.domain.entity.Position

data class RegisterRequest(
    val name: String,
    val email: String,
    val phoneNumber: String,
    val photo: String,
    val posisi: Position
)