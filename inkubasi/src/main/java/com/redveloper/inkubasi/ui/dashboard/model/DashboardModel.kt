package com.redveloper.inkubasi.ui.dashboard.model

import com.redveloper.rebator.domain.entity.StatusSeller

data class DashboardModel(
    val photoUrl: String?,
    val tiktokId: String?,
    val sellerName: String?,
    val cityName: String?,
    val districtName: String?,
    val status: StatusSeller?
)