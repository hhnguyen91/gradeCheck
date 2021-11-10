package com.example.gradecheckhhn

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.gradecheckhhn.databaseEntities.Course
import java.util.*

class CourseEditViewModel : ViewModel() {

    private val gradeCheckRepository = GradeCheckRepository.get()
    private val courseIdLiveData = MutableLiveData<UUID>()

    var courseLiveData: LiveData<Course?> =
        Transformations.switchMap(courseIdLiveData) { courseId ->
            gradeCheckRepository.getCourse(courseId)
        }

    fun loadCourse(courseId: UUID){
        courseIdLiveData.value = courseId
    }

    fun updateCourse(course: Course){
        gradeCheckRepository.updateCourse(course)
    }


}