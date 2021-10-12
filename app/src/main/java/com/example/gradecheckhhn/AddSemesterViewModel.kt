package com.example.gradecheckhhn

import androidx.lifecycle.ViewModel

class AddSemesterViewModel : ViewModel() {

    private val semesterRepository = SemesterRepository.get()

    fun addSemester(semester: Semester) {
        semesterRepository.addSemester(semester)
    }
}