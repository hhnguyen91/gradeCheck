package com.example.gradecheckhhn.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.gradecheckhhn.Course
import com.example.gradecheckhhn.Semester
import com.example.gradecheckhhn.SemesterWithManyCourses
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

    @Delete
    fun deleteSmester(semester: Semester)

    //

    /*
    @Transaction //Allow us to execute it in a thread safe manner
    @Query("Select * FROM semester where id = (:id)")
    fun getSemesterWithCourses(id:UUID) : List<SemesterWithManyCourses>
*/
}