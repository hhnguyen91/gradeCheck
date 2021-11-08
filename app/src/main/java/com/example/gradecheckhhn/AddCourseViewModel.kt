package com.example.gradecheckhhn

import androidx.lifecycle.ViewModel
import com.example.gradecheckhhn.databaseEntities.Course

class AddCourseViewModel : ViewModel() {

    private val courseRepository = CourseRepository.get()

    fun addCourse(course : Course) {
        courseRepository.addCourse(course)
    }
}