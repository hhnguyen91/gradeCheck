package com.example.gradecheckhhn.databaseEntities.relationship

import androidx.room.Embedded
import androidx.room.Relation
import com.example.gradecheckhhn.databaseEntities.Course
import com.example.gradecheckhhn.databaseEntities.Semester

data class SemesterWithManyCourses(
    @Embedded val semester : Semester,
    @Relation(
        parentColumn = "id",
        entityColumn = "SemesterID"
    )

    val courseLists : List<Course>
)
