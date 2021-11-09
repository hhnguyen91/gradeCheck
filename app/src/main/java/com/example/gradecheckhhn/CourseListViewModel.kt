package com.example.gradecheckhhn

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.gradecheckhhn.databaseEntities.Course
import java.util.*

class CourseListViewModel (private val semesterID:UUID): ViewModel() {

    private val gradeCheckRepository = GradeCheckRepository.get()
    val courseListLiveData = gradeCheckRepository.getCourses(semesterID)

    fun addCourse(course : Course) {
        Log.d("MainActivity","Hello")
        // Incomplete missing repo.add()
        gradeCheckRepository.addCourse(course)
    }
    fun deleteCourse(course: Course){
        gradeCheckRepository.deleteCourse(course)
    }

}