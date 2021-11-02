package com.example.gradecheckhhn

import androidx.room.Embedded
import androidx.room.Relation

data class SemesterWithManyCourses(
    @Embedded val semester : Semester,
    @Relation(
        parentColumn = "id",
        entityColumn = "SemesterID"
    )

    val courseLists : List<Course>
)
