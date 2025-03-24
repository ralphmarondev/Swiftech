package com.ralphmarondev.swiftech.features.home.domain.model

data class Options(
    val name: String,
    val image: Int,
    val onClick: () -> Unit
)