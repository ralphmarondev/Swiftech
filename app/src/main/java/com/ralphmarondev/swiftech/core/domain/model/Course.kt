package com.ralphmarondev.swiftech.core.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "course")
data class Course(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val code: String,
    val teacherId: Int,
    val isDeleted: Boolean = false
)

@Entity(tableName = "student_course", primaryKeys = ["studentId", "courseId"])
data class StudentCourseCrossRef(
    val studentId: Int,
    val courseId: Int
)