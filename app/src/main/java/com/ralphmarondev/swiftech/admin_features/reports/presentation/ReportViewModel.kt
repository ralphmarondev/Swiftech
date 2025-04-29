package com.ralphmarondev.swiftech.admin_features.reports.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ralphmarondev.swiftech.admin_features.reports.domain.usecase.ComputeAverageRatingUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ReportViewModel(
    private val courseId: Int,
    private val computeAverageRatingUseCase: ComputeAverageRatingUseCase
) : ViewModel() {

    private val _averageRating = MutableStateFlow(0.0)
    val averageRating = _averageRating.asStateFlow()

    init {
        viewModelScope.launch {
            Log.d("App", "Getting average rating for course with id: `$courseId`")
            computeAverageRatingUseCase(courseId).collectLatest { average ->
                _averageRating.value = average
                Log.d("App", "Average rating: ${_averageRating.value}")
            }
        }
    }
}