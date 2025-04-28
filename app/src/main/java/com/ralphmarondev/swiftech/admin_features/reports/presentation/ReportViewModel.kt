package com.ralphmarondev.swiftech.admin_features.reports.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ReportViewModel(
    private val courseId: Int
) : ViewModel() {
    init {
        viewModelScope.launch {
            Log.d("App", "Report viewmodel, course id: `$courseId`")
        }
    }
}