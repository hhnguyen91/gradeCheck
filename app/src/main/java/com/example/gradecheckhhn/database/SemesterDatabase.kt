package com.example.gradecheckhhn.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.gradecheckhhn.Semester

private const val DATABASE_NAME = "semester-database"

@Database(entities = [ Semester::class ], version = 1)
@TypeConverters(SemesterTypeConverters::class)
abstract class SemesterDatabase : RoomDatabase() {

    abstract fun semesterDao(): SemesterDao

}