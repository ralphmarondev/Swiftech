package com.ralphmarondev.swiftech.teacher_features.reports.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ralphmarondev.swiftech.admin_features.reports.domain.model.RatingCounts
import com.ralphmarondev.swiftech.admin_features.reports.domain.usecase.ComputeAverageRatingUseCase
import com.ralphmarondev.swiftech.admin_features.reports.domain.usecase.ComputeRatingCountsUseCase
import com.ralphmarondev.swiftech.core.domain.usecases.course.GetCourseDetailByIdUseCase
import com.ralphmarondev.swiftech.core.domain.usecases.user.GetUserByIdUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ReportViewModel(
    private val courseId: Int,
    private val computeAverageRatingUseCase: ComputeAverageRatingUseCase,
    private val computeRatingCountsUseCase: ComputeRatingCountsUseCase,
    private val getCourseDetailByIdUseCase: GetCourseDetailByIdUseCase,
    private val getUserDetailByIdUseCase: GetUserByIdUseCase
) : ViewModel() {

    private val _teacherName = MutableStateFlow("")
    val teacherName = _teacherName.asStateFlow()

    private val _courseName = MutableStateFlow("")
    val courseName = _courseName.asStateFlow()

    private val _averageRating = MutableStateFlow(0.0)
    val averageRating = _averageRating.asStateFlow()

    private val _ratingCounts = MutableStateFlow(RatingCounts())
    val ratingCounts = _ratingCounts.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    init {
        viewModelScope.launch {
            Log.d("App", "Loading report data for courseId: $courseId")

            val courseDetail = getCourseDetailByIdUseCase(courseId)
            Log.d("App", "Course detail: $courseDetail")
            _courseName.value = courseDetail?.name ?: "No course name provided"
            _teacherName.value = getUserDetailByIdUseCase(courseDetail?.teacherId ?: -1)?.fullName
                ?: "No teacher name provided"

            val averageJob = launch {
                val average = computeAverageRatingUseCase(courseId).first()
                _averageRating.value = average
                Log.d("App", "Average rating: $average")
            }

            val countsJob = launch {
                val counts = computeRatingCountsUseCase(courseId).first()
                _ratingCounts.value = counts
                Log.d("App", "Rating counts: $counts")
            }

            // Wait for both to emit once
            averageJob.join()
            countsJob.join()

            _isLoading.value = false
        }
    }
}
