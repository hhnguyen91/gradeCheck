package com.example.gradecheckhhn

import androidx.lifecycle.ViewModel
import com.example.gradecheckhhn.databaseEntities.Semester

class SemesterEditViewModel : ViewModel() {

    private val gradeCheckRepository = GradeCheckRepository.get()

    fun updateSemester(semester: Semester){
        gradeCheckRepository.updateSemester(semester)
    }
}