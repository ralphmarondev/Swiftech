package com.ralphmarondev.swiftech.core.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ralphmarondev.swiftech.core.domain.model.EvaluationForm
import com.ralphmarondev.swiftech.core.domain.model.EvaluationQuestion
import kotlinx.coroutines.flow.Flow

@Dao
interface EvaluationFormDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createEvaluationForm(evaluationForm: EvaluationForm)

    @Query("SELECT * FROM evaluation_form")
    fun getAllEvaluationForms(): Flow<List<EvaluationForm>>

    @Query("SELECT * FROM evaluation_form WHERE id = :id LIMIT 1")
    suspend fun getEvaluationFormById(id: Int): EvaluationForm?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveQuestionToEvaluationForm(question: EvaluationQuestion)

    @Query("SELECT last_insert_rowid()")
    suspend fun getLastInsertedId(): Long
}