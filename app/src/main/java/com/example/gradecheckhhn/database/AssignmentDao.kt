package com.example.gradecheckhhn.database

import androidx.room.Dao
import androidx.room.Update
import com.example.gradecheckhhn.databaseEntities.Assignment

@Dao
interface AssignmentDao {

    @Update
    fun updateAssignment(assignment: Assignment)
}