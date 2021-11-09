package com.example.gradecheckhhn

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.gradecheckhhn.databaseEntities.Semester
import java.util.*

class SemesterDetailViewModel() : ViewModel() {

    private val gradeCheckRepository = GradeCheckRepository.get()
    private val semesterIdLiveData = MutableLiveData<UUID>()

    var semesterLiveData: LiveData<Semester?> =
        Transformations.switchMap(semesterIdLiveData) { semesterId ->
            gradeCheckRepository.getSemester(semesterId)
        }

    fun loadSemester(semesterId: UUID) {
        semesterIdLiveData.value = semesterId
    }

    fun saveSemester(semester: Semester) {
        gradeCheckRepository.updateSemester(semester)
    }

}