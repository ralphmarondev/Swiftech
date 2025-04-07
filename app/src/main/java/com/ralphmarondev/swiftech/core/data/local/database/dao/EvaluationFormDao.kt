package com.ralphmarondev.swiftech.core.data.local.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.ralphmarondev.swiftech.core.domain.model.EvaluationForm
import kotlinx.coroutines.flow.Flow

@Dao
interface EvaluationFormDao {

    @Upsert
    suspend fun createEvaluationForm(evaluationForm: EvaluationForm)

    @Query("SELECT * FROM evaluation_form")
    fun getAllEvaluationForms(): Flow<List<EvaluationForm>>
}