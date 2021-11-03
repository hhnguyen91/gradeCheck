package com.example.gradecheckhhn.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.gradecheckhhn.Course

private const val DATABASE_NAME = "course-database"

@Database(entities = [ Course :: class], version=1)
@TypeConverters(CourseTypeConverters::class)
abstract class CourseDatabase : RoomDatabase(){

    abstract fun courseDAO(): CourseDao
}