package com.example.gradecheckhhn.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.gradecheckhhn.databaseEntities.Course
import com.example.gradecheckhhn.databaseEntities.relationship.SemesterWithManyCourses
import java.util.*

@Dao
interface CourseDao {


    @Query("SELECT * FROM semester WHERE id =(:id)")
    fun getCourses(id: UUID): LiveData<List<SemesterWithManyCourses>>

    @Query("SELECT * FROM course WHERE CourseID=(:id)")
    fun getCourse(id: UUID): LiveData<Course?>

    /*
    @Update
    fun updateCourse(course: Course)

    @Delete
    fun deleteCourse(course:Course)
    */

    @Insert
    fun addCourse(course: Course)
}