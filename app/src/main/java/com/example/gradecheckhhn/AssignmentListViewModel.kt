package com.example.gradecheckhhn

import androidx.lifecycle.ViewModel
import com.example.gradecheckhhn.databaseEntities.Assignment

class AssignmentListViewModel : ViewModel() {

    private val gradeCheckRepository = GradeCheckRepository.get()
    val assignmentListLiveData = gradeCheckRepository.getAssignments()

    fun addAssignment(assignment: Assignment) {
        //assignmentRepository.addAssignment(assignment)
        gradeCheckRepository.addAssignment(assignment)
    }

    fun deleteAssignment(assignment: Assignment) {
        gradeCheckRepository.deleteAssignment(assignment)
    }
}