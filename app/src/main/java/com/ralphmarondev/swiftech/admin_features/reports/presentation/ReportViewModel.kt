package com.ralphmarondev.swiftech.admin_features.reports.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ralphmarondev.swiftech.admin_features.reports.domain.model.RatingCounts
import com.ralphmarondev.swiftech.admin_features.reports.domain.usecase.ComputeAverageRatingUseCase
import com.ralphmarondev.swiftech.admin_features.reports.domain.usecase.ComputeRatingCountsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ReportViewModel(
    private val courseId: Int,
    private val computeAverageRatingUseCase: ComputeAverageRatingUseCase,
    private val computeRatingCountsUseCase: ComputeRatingCountsUseCase
) : ViewModel() {

    private val _averageRating = MutableStateFlow(0.0)
    val averageRating = _averageRating.asStateFlow()

    private val _ratingCounts = MutableStateFlow(RatingCounts())
    val ratingCounts = _ratingCounts.asStateFlow()

    init {
        viewModelScope.launch {
            Log.d("App", "Getting average rating for course with id: `$courseId`")
            launch {
                computeAverageRatingUseCase(courseId).collectLatest { average ->
                    _averageRating.value = average
                    Log.d("App", "Average rating: ${_averageRating.value}")
                }
            }
            launch {
                computeRatingCountsUseCase(courseId).collectLatest { counts ->
                    _ratingCounts.value = counts
                    Log.d("App", "Rating counts: `$counts`")
                }
            }
        }
    }
}