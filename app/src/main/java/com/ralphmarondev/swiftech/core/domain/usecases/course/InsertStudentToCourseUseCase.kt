package com.ralphmarondev.swiftech.core.domain.usecases.course

import android.util.Log
import com.ralphmarondev.swiftech.core.domain.model.StudentCourseCrossRef
import com.ralphmarondev.swiftech.core.domain.repositories.CourseRepository

class InsertStudentToCourseUseCase(
    private val repository: CourseRepository
) {
    suspend operator fun invoke(crossRef: StudentCourseCrossRef) {
        try {
            repository.insertStudentToCourse(crossRef)
            Log.d(
                "App",
                "Student with ID ${crossRef.studentId} enrolled in course with ID ${crossRef.courseId} successfully."
            )
        } catch (e: Exception) {
            Log.e("App", "Error enrolling student: ${e.message}")
        }
    }
}