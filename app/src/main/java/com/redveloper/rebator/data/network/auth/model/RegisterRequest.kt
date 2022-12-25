package com.redveloper.rebator.data.network.auth.model

data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String
)