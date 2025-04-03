package com.ralphmarondev.swiftech.core.domain.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "evaluation_form")
data class EvaluationForm(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String? = null,
    val isDeleted: Boolean = false
)


@Entity(
    tableName = "evaluation_question",
    foreignKeys = [ForeignKey(
        entity = EvaluationForm::class,
        parentColumns = ["id"],
        childColumns = ["evaluationFormId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class EvaluationQuestion(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val evaluationFormId: Int,
    val questionText: String
)


@Entity(
    tableName = "evaluation_response",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["studentId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Course::class,
            parentColumns = ["id"],
            childColumns = ["courseId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = EvaluationForm::class,
            parentColumns = ["id"],
            childColumns = ["evaluationFormId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class EvaluationResponse(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val studentId: Int,
    val teacherId: Int,
    val courseId: Int,
    val evaluationFormId: Int,
    val submittedAt: Long = System.currentTimeMillis()
)


@Entity(
    tableName = "evaluation_answer",
    foreignKeys = [
        ForeignKey(
            entity = EvaluationResponse::class,
            parentColumns = ["id"],
            childColumns = ["evaluationResponseId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = EvaluationQuestion::class,
            parentColumns = ["id"],
            childColumns = ["questionId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class EvaluationAnswer(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val evaluationResponseId: Int,
    val questionId: Int,
    val answer: String
)