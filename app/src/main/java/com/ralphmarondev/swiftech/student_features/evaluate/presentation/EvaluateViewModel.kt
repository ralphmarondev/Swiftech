package com.ralphmarondev.swiftech.student_features.evaluate.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ralphmarondev.swiftech.core.data.local.preferences.AppPreferences
import com.ralphmarondev.swiftech.core.domain.model.EvaluationAnswer
import com.ralphmarondev.swiftech.core.domain.model.EvaluationResponse
import com.ralphmarondev.swiftech.core.domain.model.Result
import com.ralphmarondev.swiftech.core.domain.usecases.course.GetCourseDetailByIdUseCase
import com.ralphmarondev.swiftech.core.domain.usecases.user.GetUserByIdUseCase
import com.ralphmarondev.swiftech.student_features.evaluate.domain.model.QuestionRating
import com.ralphmarondev.swiftech.student_features.evaluate.domain.model.SubmitEvaluationAnswer
import com.ralphmarondev.swiftech.student_features.evaluate.domain.usecase.GetEvaluationFormQuestionByIdUseCase
import com.ralphmarondev.swiftech.student_features.evaluate.domain.usecase.SubmitEvaluationUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class EvaluateViewModel(
    private val formId: Int,
    private val preferences: AppPreferences,
    private val getCourseDetailByIdUseCase: GetCourseDetailByIdUseCase,
    private val getUserByIdUseCase: GetUserByIdUseCase,
    private val getEvaluationFormQuestionByIdUseCase: GetEvaluationFormQuestionByIdUseCase,
    private val submitEvaluationUseCase: SubmitEvaluationUseCase
) : ViewModel() {

    private val _courseName = MutableStateFlow("")
    val courseName = _courseName.asStateFlow()

    private val _courseTeacher = MutableStateFlow("")
    val courseTeacher = _courseTeacher.asStateFlow()

    private val _questions = MutableStateFlow<List<QuestionRating>>(emptyList())
    val questions = _questions.asStateFlow()

    private val _answers = MutableStateFlow<Map<String, String>>(emptyMap())
    val answers = _answers.asStateFlow()

    private val _response = MutableStateFlow<Result?>(null)
    val response = _response.asStateFlow()

    private val _teacherId = MutableStateFlow(0)

    init {
        viewModelScope.launch {
            // NOTE: THIS IS SET ON `EVALUATION_FORMS`
            val courseId = preferences.getCourseId()

            _courseName.value =
                getCourseDetailByIdUseCase(
                    courseId
                )?.name ?: "Course name is not specified."
            val courseTeacher = getUserByIdUseCase(
                id = getCourseDetailByIdUseCase(
                    courseId
                )?.teacherId ?: 0
            )
            _courseTeacher.value = courseTeacher?.fullName ?: "Teacher name is not specified."
            _teacherId.value = courseTeacher?.id ?: 0

            getEvaluationFormQuestionByIdUseCase(formId).collect { questions ->
                _questions.value = questions.map {
                    QuestionRating(
                        id = it.id,
                        question = it.questionText
                    )
                }
            }
        }
    }

    fun selectRating(question: String, rating: String) {
        _questions.value = _questions.value.map {
            if (it.question == question) {
                it.copy(selectedRating = rating)
            } else it
        }
        _answers.value = _answers.value.toMutableMap().apply {
            this[question] = rating
        }
    }

    fun submitEvaluation() {
        viewModelScope.launch {
            try {
                val studentId = 1
                val courseId = preferences.getCourseId()
                val evaluationAnswers = _questions.value.map { questionRating ->
                    SubmitEvaluationAnswer(
                        id = questionRating.id,
                        question = questionRating.question,
                        answer = questionRating.selectedRating ?: ""
                    )
                }
                val evaluationAnswerList = evaluationAnswers.map { submitAnswer ->
                    EvaluationAnswer(
                        evaluationResponseId = 0,
                        questionId = submitAnswer.id,
                        answer = submitAnswer.answer
                    )
                }
                val evaluationResponse = EvaluationResponse(
                    studentId = studentId,
                    teacherId = _teacherId.value,
                    courseId = courseId,
                    evaluationFormId = formId
                )
                submitEvaluationUseCase(evaluationResponse, evaluationAnswerList)
                _response.value = Result(
                    success = true,
                    message = "Evaluation submitted successfully."
                )

            } catch (ex: Exception) {
                Log.e("App", "Error submitting evaluation: ${ex.message}")
                _response.value = Result(
                    success = false,
                    message = "Error submitting evaluation."
                )
            }
        }
    }
}