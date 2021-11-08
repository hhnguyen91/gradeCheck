package com.example.gradecheckhhn

import androidx.lifecycle.ViewModel
import com.example.gradecheckhhn.databaseEntities.Semester

class AddSemesterViewModel : ViewModel() {

    private val semesterRepository = SemesterRepository.get()

    fun addSemester(semester: Semester) {
        semesterRepository.addSemester(semester)
    }
}