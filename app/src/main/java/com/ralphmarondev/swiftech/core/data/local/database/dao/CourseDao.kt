package com.ralphmarondev.swiftech.core.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.ralphmarondev.swiftech.core.domain.model.Course
import com.ralphmarondev.swiftech.core.domain.model.StudentCourseCrossRef
import com.ralphmarondev.swiftech.core.domain.model.User
import kotlinx.coroutines.flow.Flow

@Dao
interface CourseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createCourse(course: Course)

    @Update
    suspend fun updateCourse(course: Course)

    @Query("UPDATE course SET isDeleted = 1 WHERE id = :id")
    suspend fun deleteCourse(id: Int)

    @Query("SELECT * FROM course WHERE id = :id AND isDeleted = 0")
    suspend fun getCourseDetailById(id: Int): Course?

    @Query("SELECT * FROM course WHERE isDeleted = 0")
    fun getAllCourses(): Flow<List<Course>>

    @Query("DELETE FROM student_course WHERE studentId = :studentId AND courseId = :courseId")
    suspend fun removeStudentFromCourse(studentId: Int, courseId: Int)

    // enroll student in a course
    @Upsert
    suspend fun insertStudentToCourse(crossRef: StudentCourseCrossRef)

    @Query(
        """
        SELECT * FROM user
        INNER JOIN student_course ON user.id = student_course.studentId
        WHERE student_course.courseId = :courseId AND user.isDeleted = 0
    """
    )
    fun getStudentsInCourse(courseId: Int): Flow<List<User>>

    // get list of courses a student is enrolled in
    @Query(
        """
            SELECT * FROM course 
            INNER JOIN student_course ON course.id = student_course.courseId
            WHERE student_course.studentId = :studentId AND course.isDeleted = 0
        """
    )
    fun getCoursesForStudent(studentId: Int): Flow<List<Course>>


    // NOTE: FOR TEACHER FEATURE
    @Query("SELECT * FROM course WHERE teacherId = :teacherId AND isDeleted = 0")
    fun getCoursesByTeacherId(teacherId: Int): Flow<List<Course>>
}