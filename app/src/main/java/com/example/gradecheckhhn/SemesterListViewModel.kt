package com.example.gradecheckhhn

import androidx.lifecycle.ViewModel
import com.example.gradecheckhhn.databaseEntities.Semester

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