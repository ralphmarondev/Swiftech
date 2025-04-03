package com.ralphmarondev.swiftech.core.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val username: String,
    val password: String,
    val role: String, // STUDENT, ADMIN, TEACHER
    val fullName: String? = null,
    val email: String? = null,
    val phoneNumber: String? = null,
    val image: String? = null,
    val isDeleted: Boolean = false
)
