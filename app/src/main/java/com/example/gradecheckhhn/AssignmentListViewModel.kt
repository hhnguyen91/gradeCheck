package com.example.gradecheckhhn

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.gradecheckhhn.databaseEntities.Assignment
import com.example.gradecheckhhn.databaseEntities.Course
import java.util.*

class AssignmentListViewModel (private val courseId: UUID): ViewModel() {

    private val gradeCheckRepository = GradeCheckRepository.get()
    val assignmentListLiveData = gradeCheckRepository.getAssignments(courseId)

    fun deleteAssignment(assignment: Assignment) {
        gradeCheckRepository.deleteAssignment(assignment)
    }
}