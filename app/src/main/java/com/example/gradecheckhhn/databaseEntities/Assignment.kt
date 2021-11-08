package com.example.gradecheckhhn.databaseEntities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Assignment(@PrimaryKey val AssignmentID : UUID = UUID.randomUUID(),
                      var assignmentName:String = "",
                      var breakdownName:String = "",
                      var currentPoints: Double = 0.0,
                      var maximumPoints: Double = 0.0)
