package com.example.gradecheckhhn

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.gradecheckhhn.databaseEntities.Course

class CourseListViewModel : ViewModel() {

    private val courseRepository = CourseRepository.get()
    val courseListLiveData = courseRepository.getCourses()

    fun addCourse(course : Course) {
        Log.d("MainActivity","Hello")
        // Incomplete missing repo.add()
    }
}