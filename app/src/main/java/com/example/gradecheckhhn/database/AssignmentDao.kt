package com.example.gradecheckhhn.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.gradecheckhhn.databaseEntities.Assignment
import com.example.gradecheckhhn.databaseEntities.relationship.CourseWithManyAssignments
import java.util.*

@Dao
interface AssignmentDao {

    @Query("SELECT * FROM course WHERE CourseID =(:id)")
    fun getAssignments(id: UUID): LiveData<List<CourseWithManyAssignments>>

//    @Query("SELECT * FROM assignment")
//    fun getAssignments(): LiveData<List<Assignment>>

    @Query("SELECT * FROM assignment WHERE AssignmentID=(:id)")
    fun getAssignment(id: UUID): LiveData<Assignment?>

    @Insert
    fun addAssignment(assignment: Assignment)

    @Update
    fun updateAssignment(assignment: Assignment)

    @Delete
    fun deleteAssignment(assignment: Assignment)
}