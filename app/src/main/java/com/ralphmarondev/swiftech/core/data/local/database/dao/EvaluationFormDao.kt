package com.ralphmarondev.swiftech.core.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.ralphmarondev.swiftech.core.domain.model.EvaluationAnswer
import com.ralphmarondev.swiftech.core.domain.model.EvaluationForm
import com.ralphmarondev.swiftech.core.domain.model.EvaluationQuestion
import com.ralphmarondev.swiftech.core.domain.model.EvaluationResponse
import kotlinx.coroutines.flow.Flow

@Dao
interface EvaluationFormDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createEvaluationForm(evaluationForm: EvaluationForm): Long

    @Update
    suspend fun updateEvaluationForm(evaluationForm: EvaluationForm)

    @Query("SELECT * FROM evaluation_form WHERE isDeleted = 0")
    fun getAllEvaluationForms(): Flow<List<EvaluationForm>>

    @Query("SELECT * FROM evaluation_form WHERE id = :id AND isDeleted = 0 LIMIT 1")
    suspend fun getEvaluationFormById(id: Int): EvaluationForm?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveQuestionToEvaluationForm(question: EvaluationQuestion)

    @Query("SELECT last_insert_rowid()")
    suspend fun getLastInsertedId(): Long

    @Query("UPDATE evaluation_form SET isDeleted = 1 where id = :evalutionFormId")
    suspend fun deleteEvaluationForm(evalutionFormId: Int)

    @Query("SELECT * FROM evaluation_question WHERE evaluationFormId = :id")
    fun getQuestionsByEvaluationId(id: Int): Flow<List<EvaluationQuestion>>

    @Query("DELETE FROM evaluation_question WHERE id = :id")
    suspend fun deleteQuestionById(id: Int)

    @Update
    suspend fun updateQuestionById(question: EvaluationQuestion)


    // NOTE: FUNCTIONS FOR STUDENT FEATURE
    @Query("SELECT * FROM evaluation_form WHERE term = :term AND isDeleted = 0")
    fun getEvaluationFormsByTerm(term: String): Flow<List<EvaluationForm>>

    @Query("SELECT * FROM evaluation_form WHERE id = :id AND isDeleted = 0 LIMIT 1")
    suspend fun getEvaluationFormDetailById(id: Int): EvaluationForm

    @Query("SELECT * FROM evaluation_question WHERE evaluationFormId = :id")
    fun getEvaluationFormQuestionsById(id: Int): Flow<List<EvaluationQuestion>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvaluationResponse(response: EvaluationResponse): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvaluationAnswer(answers: List<EvaluationAnswer>)

    @Transaction
    suspend fun submitEvaluation(response: EvaluationResponse, answers: List<EvaluationAnswer>) {
        val responseId = insertEvaluationResponse(response)
        val answersWithResponseId =
            answers.map { it.copy(evaluationResponseId = responseId.toInt()) }
        insertEvaluationAnswer(answersWithResponseId)
    }

    @Query(
        """
            SELECT COUNT(*) FROM evaluation_response
            WHERE studentId = :studentId AND courseId = :courseId AND evaluationFormId = :evaluationFormId
        """
    )
    suspend fun hasStudentAlreadyEvaluated(
        studentId: Int,
        courseId: Int,
        evaluationFormId: Int
    ): Int


    // NOTE: THIS IS FOR ADMIN
    //
    //      QUERY IF YOU WANT TO INCLUDE ALL EVALUATION RESPONSES EVEN FROM DELETED FORMS.
    //          SELECT evaluation_answer.* FROM evaluation_answer
    //            INNER JOIN evaluation_response
    //            ON evaluation_answer.evaluationResponseId = evaluation_response.id
    //            WHERE evaluation_response.courseId = :courseId
    @Query(
        """
            SELECT evaluation_answer.* FROM evaluation_answer
            INNER JOIN evaluation_response
                ON evaluation_answer.evaluationResponseId = evaluation_response.id 
            INNER JOIN evaluation_form
                ON evaluation_response.evaluationFormId = evaluation_form.id
            WHERE evaluation_response.courseId = :courseId
            AND evaluation_form.isDeleted = 0
        """
    )
    fun getEvaluationAnswersByCourse(courseId: Int): Flow<List<EvaluationAnswer>>
}