package com.ralphmarondev.swiftech.core.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val role: String,
    val username: String,
    val password: String,
    val fullName: String? = null,
    val image: String? = null
)
