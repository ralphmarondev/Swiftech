package com.ralphmarondev.swiftech.features.students.presentation.student_list

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ralphmarondev.swiftech.R
import com.ralphmarondev.swiftech.core.domain.model.User
import com.ralphmarondev.swiftech.core.util.saveDrawableToInternalStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class StudentListViewModel(
    private val context: Context
) : ViewModel() {
    private val _students = MutableStateFlow<List<User>>(emptyList())
    val students = _students.asStateFlow()

    init {
        viewModelScope.launch {
            val imagePath = saveDrawableToInternalStorage(
                context = context,
                drawableRes = R.drawable.profile,
                fileName = "profile.jpg"
            )
            val dummyStudents = listOf(
                User(
                    username = "jam",
                    password = "jam",
                    role = "Student",
                    fullName = "Jamille Rivera",
                    image = imagePath
                ),
                User(
                    username = "ralph",
                    password = "ralph",
                    role = "Student",
                    fullName = "Ralph Maron Eda",
                    image = imagePath
                )
            )

            for (student in dummyStudents) {
                _students.value += student
            }
        }
    }
}