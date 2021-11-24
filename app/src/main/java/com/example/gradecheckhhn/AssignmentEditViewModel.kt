package com.example.gradecheckhhn

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.gradecheckhhn.databaseEntities.Assignment
import java.util.*

class AssignmentEditViewModel : ViewModel() {

    private val gradeCheckRepository = GradeCheckRepository.get()
    private val assignmentIdLiveData = MutableLiveData<UUID>()

    var assignmentLiveData: LiveData<Assignment?> =
        Transformations.switchMap(assignmentIdLiveData) { assignmentId ->
            gradeCheckRepository.getAssignment(assignmentId)
        }

    fun loadAssignment(assignmentId: UUID){
        assignmentIdLiveData.value = assignmentId
    }

    fun updateAssignment(assignment: Assignment){
        gradeCheckRepository.updateAssignment(assignment)
    }
}
