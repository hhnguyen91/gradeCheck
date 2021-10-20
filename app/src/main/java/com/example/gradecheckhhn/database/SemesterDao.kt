package com.example.gradecheckhhn.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.gradecheckhhn.Course
import com.example.gradecheckhhn.Semester
import java.util.*

@Dao
interface SemesterDao{

    //
    @Query("SELECT * FROM semester")
    fun getSemesters(): LiveData<List<Semester>>

    @Query("SELECT * FROM semester WHERE id=(:id)")
    fun getSemester(id: UUID): LiveData<Semester?>

    @Update
    fun updateSemester(semester: Semester)

    @Insert
    fun addSemester(semester: Semester)
    //

    //

    /*
    @Query("SELECT * FROM course")
    fun getCourses(): LiveData<List<Course>>

    @Query("SELECT * FROM course WHERE CourseID=(:id)")
    fun getCourse(id: UUID): LiveData<Course?>

    @Update
    fun updateCourse(course: Course)

    @Insert
    fun addCourse(course: Course)
    */

}