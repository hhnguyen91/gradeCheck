package com.example.gradecheckhhn.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.gradecheckhhn.databaseEntities.Assignment
import com.example.gradecheckhhn.databaseEntities.Course
import com.example.gradecheckhhn.databaseEntities.Semester

private const val DATABASE_NAME = "semester-database"

@Database(entities = [ Semester::class, Course::class , Assignment::class], version = 1)
@TypeConverters(DBTypeConverters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun semesterDao(): SemesterDao
    abstract fun courseDao() : CourseDao
    abstract fun assignmentDao() : AssignmentDao
}