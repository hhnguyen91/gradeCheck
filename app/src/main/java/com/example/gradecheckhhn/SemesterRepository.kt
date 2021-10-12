package com.example.gradecheckhhn

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.example.gradecheckhhn.database.SemesterDatabase
import java.lang.IllegalStateException
import java.util.*
import java.util.concurrent.Executor
import java.util.concurrent.Executors

private const val DATABASE_NAME = "semester-database"

class SemesterRepository private constructor(context: Context){

    private val database : SemesterDatabase = Room.databaseBuilder(
        context.applicationContext,
        SemesterDatabase::class.java,
        DATABASE_NAME
    ).build()

    private val semesterDao = database.semesterDao()
    private val executor = Executors.newSingleThreadExecutor()

    fun getSemesters(): LiveData<List<Semester>> = semesterDao.getSemesters()

    fun getSemester(id: UUID): LiveData<Semester?> = semesterDao.getSemester(id)

    fun updateSemester(semester: Semester) {
        executor.execute {
            semesterDao.updateSemester(semester)
        }
    }

    fun addSemester(semester: Semester) {
        executor.execute {
            semesterDao.addSemester(semester)
        }
    }

    companion object {
        private var INSTANCE: SemesterRepository? = null

        //Makes the SemesterRepository and singleton
        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = SemesterRepository(context)
            }
        }

        fun get(): SemesterRepository {
            return INSTANCE ?:
            throw IllegalStateException("SemesterRepository must be initialized")
        }
    }
}