package com.example.gradecheckhhn

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.example.gradecheckhhn.database.AppDatabase
import com.example.gradecheckhhn.databaseEntities.Course
import java.lang.IllegalStateException
import java.util.*
import java.util.concurrent.Executors

private const val DATABASE_NAME = "course-database"
class CourseRepository private constructor(context: Context) {

    private val database : AppDatabase = Room.databaseBuilder(
        context.applicationContext,
        AppDatabase::class.java,
        DATABASE_NAME
    ).build()

    private val courseDao = database.courseDao()
    private val executor = Executors.newSingleThreadExecutor()

    fun getCourses(): LiveData<List<Course>> = courseDao.getCourses()

    fun getCourse(id: UUID): LiveData<Course?> = courseDao.getCourse(id)

    fun addCourse(course: Course) {
        executor.execute {
            courseDao.addCourse(course)
        }
    }

    companion object {
        private var INSTANCE: CourseRepository? = null

        fun initialize(context: Context) {
            if(INSTANCE == null){
                INSTANCE = CourseRepository(context)
            }
        }

        fun get(): CourseRepository {
            return INSTANCE ?:
            throw IllegalStateException("CourseRepository must be initialized")
        }
    }
}