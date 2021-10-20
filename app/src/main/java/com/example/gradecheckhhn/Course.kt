package com.example.gradecheckhhn

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Course (@PrimaryKey val CourseID: UUID = UUID.randomUUID(),
                   //@ForeignKey val semesterCourseID: UUID,
                   var courseName: String = "",
                   var department: String = "",
                   var sectionNumber: Number = 0)