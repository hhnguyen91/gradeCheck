package com.example.gradecheckhhn.databaseEntities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.*

@Entity( foreignKeys = [ForeignKey(
    entity = Course::class,
    parentColumns = arrayOf("CourseID"),
    childColumns = arrayOf("CourseID"),
    onDelete = ForeignKey.CASCADE
)]
)
data class Assignment(@PrimaryKey val AssignmentID : UUID = UUID.randomUUID(),

                      var CourseID: String = "",

                      var assignmentName:String = "",
                      var breakdownName:String = "",
                      var currentPoints: Double = 0.0,
                      var maximumPoints: Double = 0.0)
