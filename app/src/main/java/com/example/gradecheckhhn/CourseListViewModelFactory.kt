package com.example.gradecheckhhn

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException
import java.util.*

class CourseListViewModelFactory(private val semesterID:UUID) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(CourseListViewModel::class.java)){
            return CourseListViewModel(semesterID) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }

}