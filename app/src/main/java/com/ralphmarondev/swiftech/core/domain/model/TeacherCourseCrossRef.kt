package com.ralphmarondev.swiftech.core.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "teacher_course",
    primaryKeys = ["teacherId", "courseId"]
)
data class TeacherCourseCrossRef(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val teacherId: Int,
    val courseId: Int
)
