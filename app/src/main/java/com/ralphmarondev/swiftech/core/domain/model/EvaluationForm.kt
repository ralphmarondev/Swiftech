package com.ralphmarondev.swiftech.core.domain.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "evaluation_form")
data class EvaluationForm(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val createdByAdminId: Int,
    val isDeleted: Boolean = false
)

@Entity(
    tableName = "question",
    foreignKeys = [ForeignKey(
        entity = EvaluationForm::class,
        parentColumns = ["id"],
        childColumns = ["evaluationFormId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Question(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val evaluationFormId: Int,
    val questionText: String
)

data class Evaluation(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val studentId: Int,
    val teacherId: Int,
    val courseId: Int,
    val evaluationFormId: Int,
    val submittedAt: Long = System.currentTimeMillis()
)