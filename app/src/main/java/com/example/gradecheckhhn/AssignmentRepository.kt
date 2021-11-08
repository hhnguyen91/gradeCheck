package com.example.gradecheckhhn

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.example.gradecheckhhn.database.AppDatabase
import com.example.gradecheckhhn.databaseEntities.Assignment
import java.lang.IllegalStateException
import java.util.*
import java.util.concurrent.Executors

private const val DATABASE_NAME = "assignment-database"

class AssignmentRepository private constructor(context: Context) {

    private val database : AppDatabase = Room.databaseBuilder(
        context.applicationContext,
        AppDatabase::class.java,
        DATABASE_NAME
    ).build()

    private val assignmentDao = database.assignmentDao()
    private val executor = Executors.newSingleThreadExecutor()

    fun getAssignments(): LiveData<List<Assignment>> = assignmentDao.getAssignments()

    fun getAssignment(id: UUID): LiveData<Assignment?> = assignmentDao.getAssignment(id)
/*
    fun addAssignment(assignment: Assignment) {
        executor.execute {
            assignmentDao.addAssignment(assignment)
        }
    }
*/
    fun deleteAssignment(assignment: Assignment) {
        executor.execute {
            assignmentDao.deleteAssignment(assignment)
        }
    }

    companion object {
        private var INSTANCE: AssignmentRepository? = null

        fun initialize(context: Context) {
            if(INSTANCE == null){
                INSTANCE = AssignmentRepository(context)
            }
        }

        fun get(): AssignmentRepository {
            return INSTANCE ?:
            throw IllegalStateException("AssignmentRepository must be initialized")
        }
    }
}