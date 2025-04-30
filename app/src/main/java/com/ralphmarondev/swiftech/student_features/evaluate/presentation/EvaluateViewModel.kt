package com.ralphmarondev.swiftech.student_features.evaluate.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ralphmarondev.swiftech.core.data.local.preferences.AppPreferences
import com.ralphmarondev.swiftech.core.domain.model.EvaluationAnswer
import com.ralphmarondev.swiftech.core.domain.model.EvaluationForm
import com.ralphmarondev.swiftech.core.domain.model.EvaluationResponse
import com.ralphmarondev.swiftech.core.domain.model.Result
import com.ralphmarondev.swiftech.core.domain.usecases.course.GetCourseDetailByIdUseCase
import com.ralphmarondev.swiftech.core.domain.usecases.user.GetUserByIdUseCase
import com.ralphmarondev.swiftech.student_features.evaluate.domain.model.QuestionRating
import com.ralphmarondev.swiftech.student_features.evaluate.domain.model.SubmitEvaluationAnswer
import com.ralphmarondev.swiftech.student_features.evaluate.domain.usecase.GetEvaluationFormDetailByIdUseCase
import com.ralphmarondev.swiftech.student_features.evaluate.domain.usecase.GetEvaluationFormQuestionByIdUseCase
import com.ralphmarondev.swiftech.student_features.evaluate.domain.usecase.HasEvaluatedUseCase
import com.ralphmarondev.swiftech.student_features.evaluate.domain.usecase.SubmitEvaluationUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class EvaluateViewModel(
    private val evaluationFormId: Int,
    private val preferences: AppPreferences,
    private val getCourseDetailByIdUseCase: GetCourseDetailByIdUseCase,
    private val getUserByIdUseCase: GetUserByIdUseCase,
    private val getEvaluationFormQuestionByIdUseCase: GetEvaluationFormQuestionByIdUseCase,
    private val submitEvaluationUseCase: SubmitEvaluationUseCase,
    private val hasEvaluatedUseCase: HasEvaluatedUseCase,
    private val getEvaluationFormDetailByIdUseCase: GetEvaluationFormDetailByIdUseCase
) : ViewModel() {

    private val _courseName = MutableStateFlow("")
    val courseName = _courseName.asStateFlow()

    private val _courseTeacher = MutableStateFlow("")
    val courseTeacher = _courseTeacher.asStateFlow()

    private val _evalutionFormDetail = MutableStateFlow<EvaluationForm?>(null)
    val evaluationForm = _evalutionFormDetail.asStateFlow()

    private val _questions = MutableStateFlow<List<QuestionRating>>(emptyList())
    val questions = _questions.asStateFlow()

    private val _answers = MutableStateFlow<Map<String, String>>(emptyMap())
    val answers = _answers.asStateFlow()

    private val _response = MutableStateFlow<Result?>(null)
    val response = _response.asStateFlow()

    private val _teacherId = MutableStateFlow(0)

    private val _hasEvaluated = MutableStateFlow(true)
    val hasEvaluated = _hasEvaluated.asStateFlow()

    private val _showEvaluationResultDialog = MutableStateFlow(false)
    val showEvaluationResultDialog = _showEvaluationResultDialog.asStateFlow()

    private val _showSubmitConfirmationDialog = MutableStateFlow(false)
    val showSubmitConfirmationDialog = _showSubmitConfirmationDialog.asStateFlow()

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
            _evalutionFormDetail.value = getEvaluationFormDetailByIdUseCase(evaluationFormId)

            val hasEvaluated = hasEvaluatedUseCase(
                courseId = courseId,
                studentId = preferences.getStudentId(),
                evaluationFormId = evaluationFormId
            )

            _hasEvaluated.value = hasEvaluated
            if (hasEvaluated) {
                Log.d("App", "Student has already evaluated. Returning...")
                return@launch
            }

            Log.d("App", "Student has not evaluated yet. Getting evaluation forms...")
            getEvaluationFormQuestionByIdUseCase(evaluationFormId).collect { questions ->
                _questions.value = questions.map {
                    QuestionRating(
                        id = it.id,
                        question = it.questionText
                    )
                }
            }
            Log.d("App", "Form count: ${_questions.value.size}")
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
            if (_hasEvaluated.value) {
                Log.d("App", "Cannot submit, student has already evaluated.")
                return@launch
            }

            // NOTE: we need to check if all questions are answered before submitting
            val unansweredQuestions = _questions.value.filter { it.selectedRating == null }
            if (unansweredQuestions.isNotEmpty()) {
                Log.d("App", "Cannot submit, not all questions are answered.")
                _response.value = Result(
                    success = false,
                    message = "Please answer all questions before submitting."
                )
                return@launch
            }

            try {
                // NOTE: studentId is set on HomeViewModel
                val studentId = preferences.getStudentId()
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
                    evaluationFormId = evaluationFormId
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

    fun showEvaluationResultDialogValueChange() {
        _showEvaluationResultDialog.value = !_showEvaluationResultDialog.value
    }

    fun showSubmitConfirmationDialogValueChange() {
        _showSubmitConfirmationDialog.value = !_showSubmitConfirmationDialog.value
    }
}