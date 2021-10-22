package com.example.gradecheckhhn.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.gradecheckhhn.Course
import java.util.*

@Dao
interface CourseDao {


    @Query("SELECT * FROM course")
    fun getCourses(): LiveData<List<Course>>

    @Query("SELECT * FROM course WHERE CourseID=(:id)")
    fun getCourse(id: UUID): LiveData<Course?>

    @Update
    fun updateCourse(course: Course)

    @Insert
    fun addCourse(course: Course)


}