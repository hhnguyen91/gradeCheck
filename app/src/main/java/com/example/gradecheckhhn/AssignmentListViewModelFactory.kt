package com.example.gradecheckhhn

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException
import java.util.*

class AssignmentListViewModelFactory(private val courseID: UUID) :  ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(AssignmentListViewModel::class.java)){
            return AssignmentListViewModel(courseID) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }

}