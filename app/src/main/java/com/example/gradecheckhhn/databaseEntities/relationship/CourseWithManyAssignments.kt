package com.example.gradecheckhhn.databaseEntities.relationship

import androidx.room.Embedded
import androidx.room.Relation
import com.example.gradecheckhhn.databaseEntities.Course
import com.example.gradecheckhhn.databaseEntities.Assignment

data class CourseWithManyAssignments(
    @Embedded val course : Course,
    @Relation(
        parentColumn = "CourseID",
        entityColumn =  "AssignmentID"
    )
    val assignmentList: List<Assignment>
)
