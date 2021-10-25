package com.example.gradecheckhhn

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentManager
import java.util.*

private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity(),
    SemesterListFragment.Callbacks, AddSemesterFragment.Callbacks
    ,SemesterFragment.Callbacks, AddCourseFragment.Callbacks{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fm : FragmentManager = supportFragmentManager
        val currentFragment =
            fm.findFragmentById(R.id.fragment_container)

        if (currentFragment == null) {
            val fragment = SemesterListFragment()
            fm
                .beginTransaction()
                .add(R.id.fragment_container, fragment)
                .commit()
        }
    }

    override fun onSemesterSelected(semesterId: UUID) {
        Log.d(TAG, "MainActivity.onCrimeSelected: $semesterId")
        val fragment = SemesterFragment.newInstance(semesterId)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onAddSemesterSelected() {
        val fragment = AddSemesterFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container,fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onAddSemesterButtonClicked() {
        Log.d(TAG,"Add Semester")
        val fragment = SemesterListFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onCourseSelected(courseId: UUID) {
        Log.d(TAG,"MainActivity.onClassSelected: $courseId")
        val fragment = CourseFragment.newInstance(courseId)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onAddCourseSelected() {
        val fragment = AddCourseFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container,fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onAddCourseButtonClicked() {
        Log.d(TAG,"Add Course")
        val fragment = SemesterFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

}