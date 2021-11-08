package com.example.gradecheckhhn

import androidx.lifecycle.ViewModel
import com.example.gradecheckhhn.databaseEntities.Assignment

class AssignmentListViewModel : ViewModel() {

    private val assignmentRepository = AssignmentRepository.get()
    val assignmentListLiveData = assignmentRepository.getAssignments()
/*
    fun addAssignment(assignment: Assignment) {
        assignmentRepository.addAssignment(assignment)
    }
*/
    fun deleteAssignment(assignment: Assignment) {
        assignmentRepository.deleteAssignment(assignment)
    }
}