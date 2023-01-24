package com.redveloper.inkubasi.ui.filterseller.model

data class FilterSellerModel(
    var genders: HashSet<Pair<String, String>>? = null,
    var status: HashSet<Pair<String, String>>? = null
)