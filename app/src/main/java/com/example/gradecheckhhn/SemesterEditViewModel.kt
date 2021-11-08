package com.example.gradecheckhhn

import androidx.lifecycle.ViewModel
import com.example.gradecheckhhn.databaseEntities.Semester

class SemesterEditViewModel : ViewModel() {

    private val semesterRepository = SemesterRepository.get()

    fun updateSemester(semester: Semester){
        semesterRepository.updateSemester(semester)
    }
}