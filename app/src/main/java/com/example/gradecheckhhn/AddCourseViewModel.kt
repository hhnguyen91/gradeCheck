package com.example.gradecheckhhn

import androidx.lifecycle.ViewModel

class AddCourseViewModel : ViewModel() {

    private val courseRepository = CourseRepository.get()

    fun addCourse(course : Course) {
        courseRepository.addCourse(course)
    }
}