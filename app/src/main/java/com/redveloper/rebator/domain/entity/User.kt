package com.redveloper.rebator.domain.entity

data class User(
    val email: String,
    val name: String,
    val photoUrl: String,
    val position: Position,
    val phoneNumber: String
)
