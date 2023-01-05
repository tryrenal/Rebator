package com.redveloper.rebator.domain.entity

data class User(
    val email: String,
    val name: String,
    val photo: String,
    val posisi: Position,
    val phoneNumber: String
)
