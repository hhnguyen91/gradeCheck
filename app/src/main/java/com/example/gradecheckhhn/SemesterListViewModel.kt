package com.example.gradecheckhhn

import androidx.lifecycle.ViewModel
import com.example.gradecheckhhn.databaseEntities.Semester

class SemesterListViewModel : ViewModel () {

    private val gradeCheckRepository = GradeCheckRepository.get()
    val semesterListLiveData = gradeCheckRepository.getSemesters()

    fun addSemester(semester: Semester) {
        gradeCheckRepository.addSemester(semester)
    }

    fun deleteSemester(semester: Semester) {
        gradeCheckRepository.deleteSemester(semester)
    }
}