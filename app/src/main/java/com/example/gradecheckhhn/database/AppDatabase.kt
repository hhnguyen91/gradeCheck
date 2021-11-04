package com.example.gradecheckhhn.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.gradecheckhhn.Course
import com.example.gradecheckhhn.Semester

private const val DATABASE_NAME = "semester-database"

@Database(entities = [ Semester::class, Course::class ], version = 1)
@TypeConverters(DBTypeConverters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun semesterDao(): SemesterDao
    abstract fun courseDao() : CourseDao

}