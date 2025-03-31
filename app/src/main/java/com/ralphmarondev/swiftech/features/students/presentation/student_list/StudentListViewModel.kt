package com.ralphmarondev.swiftech.features.students.presentation.student_list

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ralphmarondev.swiftech.R
import com.ralphmarondev.swiftech.core.data.local.preferences.AppPreferences
import com.ralphmarondev.swiftech.core.domain.model.User
import com.ralphmarondev.swiftech.core.domain.usecases.GetAllUserByRoleUseCase
import com.ralphmarondev.swiftech.core.util.saveDrawableToInternalStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class StudentListViewModel(
    private val getAllUserByRole: GetAllUserByRoleUseCase,
    private val preferences: AppPreferences
) : ViewModel(), KoinComponent {
    private val context: Context by inject()
    private val _students = MutableStateFlow<List<User>>(emptyList())
    val students = _students.asStateFlow()

    init {
        viewModelScope.launch {
            val imagePath = saveDrawableToInternalStorage(
                context = context,
                drawableRes = R.drawable.profile,
                fileName = "profile.jpg"
            )
            preferences.setDefaultImage(imagePath)
            getAllUserByRole(role = "Student").collect { studentList ->
                _students.value = studentList.map { student ->
                    student.copy(image = imagePath)
                }
            }
        }
    }
}