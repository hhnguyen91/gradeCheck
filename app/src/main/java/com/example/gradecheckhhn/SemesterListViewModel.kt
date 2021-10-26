package com.example.gradecheckhhn

import androidx.lifecycle.ViewModel
import java.util.*

class SemesterListViewModel : ViewModel () {

    private val semesterRepository = SemesterRepository.get()
    val semesterListLiveData = semesterRepository.getSemesters()

    fun addSemester(semester: Semester) {
        semesterRepository.addSemester(semester)
    }

    fun deleteSemester(semester: Semester) {
        semesterRepository.deleteSemester(semester)
    }
}