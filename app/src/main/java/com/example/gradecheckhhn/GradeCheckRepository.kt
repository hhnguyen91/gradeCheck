package com.example.gradecheckhhn

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.example.gradecheckhhn.database.AppDatabase
import com.example.gradecheckhhn.databaseEntities.Assignment
import com.example.gradecheckhhn.databaseEntities.Course
import com.example.gradecheckhhn.databaseEntities.Semester
import com.example.gradecheckhhn.databaseEntities.relationship.SemesterWithManyCourses
import java.lang.IllegalStateException
import java.util.*
import java.util.concurrent.Executors

private const val DATABASE_NAME = "gradecheck-database"

class GradeCheckRepository private constructor(context: Context){

    private val database : AppDatabase = Room.databaseBuilder(
        context.applicationContext,
        AppDatabase::class.java,
        DATABASE_NAME
    ).build()

    private val semesterDao = database.semesterDao()
    private val courseDao = database.courseDao()
    private val assignmentDao = database.assignmentDao()
    private val executor = Executors.newSingleThreadExecutor()

    //Semester Functions
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

    fun deleteSemester(semester: Semester){
        executor.execute{
            semesterDao.deleteSemester(semester)
        }
    }

    //Course Functions
    fun getCourses(id:UUID): LiveData<List<SemesterWithManyCourses>> = courseDao.getCourses(id)

    fun getCourse(id: UUID): LiveData<Course?> = courseDao.getCourse(id)

    fun updateCourse(course: Course) {
        executor.execute {
            courseDao.updateCourse(course)
        }
    }

    fun addCourse(course: Course) {
        executor.execute {
            courseDao.addCourse(course)
        }
    }

    fun deleteCourse(course: Course){
        executor.execute{
            courseDao.deleteCourse(course)
        }
    }

    //Assignment functions
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
        private var INSTANCE: GradeCheckRepository? = null

        //Makes the SemesterRepository and singleton
        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = GradeCheckRepository(context)
            }
        }

        fun get(): GradeCheckRepository {
            return INSTANCE ?:
            throw IllegalStateException("GradeCheckRepository must be initialized")
        }
    }
}