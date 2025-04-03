package com.ralphmarondev.swiftech.core.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "student_course",
    primaryKeys = ["studentId", "courseId"]
)
data class StudentCourseCrossRef(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val studentId: Int,
    val courseId: Int
)
