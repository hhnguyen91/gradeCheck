package com.example.gradecheckhhn

import android.util.Log
import androidx.lifecycle.ViewModel

class CourseListViewModel : ViewModel() {

    var courses = mutableListOf<Course>()

    init {
        for (i in 0 until 5)
        {
            //val course = Course()
            //course.courseName = "Class #$i"
            //course.department = "CECS"
            //course.sectionNumber = 9999;
        }
    }

    fun addCourse(course :Course) {
        Log.d("MainActivity","Hello")
        // Incomplete missing repo.add()
    }
}