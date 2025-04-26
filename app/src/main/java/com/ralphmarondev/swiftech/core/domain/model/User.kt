package com.ralphmarondev.swiftech.core.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val username: String,
    val password: String,
    val gender: String = Gender.MALE, // Male, Female; [Default: Male]
    val fullName: String? = null,
    val role: String = Role.STUDENT, // STUDENT, ADMIN, TEACHER; [Default Student]
    val email: String? = null,
    val phoneNumber: String? = null,
    val image: String? = null,
    val isDeleted: Boolean = false
)
