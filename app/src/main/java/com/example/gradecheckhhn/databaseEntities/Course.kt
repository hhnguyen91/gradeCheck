package com.example.gradecheckhhn.databaseEntities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import java.util.*

@Entity ( foreignKeys = [ForeignKey(
    entity = Semester::class,
    parentColumns = arrayOf("id"),
    childColumns = arrayOf("SemesterID"),
    onDelete = ForeignKey.CASCADE
)]
)
data class Course (
        @PrimaryKey val CourseID: UUID = UUID.randomUUID(),
                   var SemesterID: String = "",
                   var courseName: String = "",
// List of Breakdown Var go here 6 Breakdown
                   var breakdown1Name : String = "",
                   var breakdown1Weight : Double = 0.0,

                   var breakdown2Name : String = "",
                   var breakdown2Weight : Double = 0.0,

                   var breakdown3Name : String = "",
                   var breakdown3Weight : Double = 0.0,

                   var breakdown4Name : String = "",
                   var breakdown4Weight : Double = 0.0,

                   var breakdown5Name : String = "",
                   var breakdown5Weight : Double = 0.0,

                   var breakdown6Name : String = "Final",
                   var breakdown6Weight : Double = 0.0,
// A Grade Rate
                   var maxA: Double = 100.0,
                   var minA: Double = 90.0,
// B Grade Rate
                   var maxB: Double = 89.0,
                   var minB: Double = 80.0,
// C Grade Rate
                   var maxC: Double = 79.0,
                   var minC: Double = 70.0,
// D Grade Rate
                   var maxD: Double = 69.0,
                   var minD: Double = 60.0,
// F Grade Rate
                   var maxF: Double = 59.0,
                   var minF: Double = 0.0
)
