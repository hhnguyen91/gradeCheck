package com.example.gradecheckhhn

import androidx.lifecycle.ViewModel
import com.example.gradecheckhhn.databaseEntities.Assignment

class AddAssignmentViewModel : ViewModel() {

    private val gradeCheckRepository = GradeCheckRepository.get()

    fun addAssignment(assignment: Assignment) {
        gradeCheckRepository.addAssignment(assignment)
    }
}
