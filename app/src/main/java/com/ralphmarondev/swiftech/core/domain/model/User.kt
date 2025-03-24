package com.ralphmarondev.swiftech.core.domain.model

data class User(
    val id: Int = 0,
    val role: String,
    val username: String,
    val password: String,
    val fullName: String? = null,
    val image: String? = null
)
