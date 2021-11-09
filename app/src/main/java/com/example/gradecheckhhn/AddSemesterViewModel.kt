package com.example.gradecheckhhn

import androidx.lifecycle.ViewModel
import com.example.gradecheckhhn.databaseEntities.Semester

class AddSemesterViewModel : ViewModel() {

    private val gradeCheckRepository = GradeCheckRepository.get()

    fun addSemester(semester: Semester) {
        gradeCheckRepository.addSemester(semester)
    }
}