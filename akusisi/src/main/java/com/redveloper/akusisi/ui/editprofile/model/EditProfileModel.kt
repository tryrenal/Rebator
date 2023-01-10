package com.redveloper.akusisi.ui.editprofile.model

import com.redveloper.rebator.domain.entity.Position

data class EditProfileModel(
    val name: String? = null,
    val photoUri: String? = null,
    val phoneNumber: String? = null,
    val position: Position? = null
)