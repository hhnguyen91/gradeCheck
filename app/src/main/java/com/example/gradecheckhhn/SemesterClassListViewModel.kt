package com.example.gradecheckhhn

import androidx.lifecycle.ViewModel

class SemesterClassListViewModel : ViewModel() {

    var SemesterClasses = mutableListOf<SemesterClass>()

    init {
        for (i in 0 until 5)
        {
            val semesterClass = SemesterClass()
            semesterClass.className = "Class #$i"
            semesterClass.department = "CECS"
            semesterClass.sectionNumber = 9999;
        }
    }
}