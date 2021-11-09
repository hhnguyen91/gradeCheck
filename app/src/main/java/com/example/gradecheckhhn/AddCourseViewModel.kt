package com.example.gradecheckhhn

import androidx.lifecycle.ViewModel
import com.example.gradecheckhhn.databaseEntities.Course

class AddCourseViewModel : ViewModel() {

    private val gradeCheckRepository = GradeCheckRepository.get()

    fun addCourse(course : Course) {
        gradeCheckRepository.addCourse(course)
    }
}