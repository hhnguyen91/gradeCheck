package com.example.gradecheckhhn

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentManager
import java.util.*

private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity(),
    SemesterListFragment.Callbacks, AddSemesterFragment.Callbacks
    ,SemesterClassListFragment.Callbacks{

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
        val fragment = SemesterListFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onClassSelected(classId: UUID) {
        Log.d(TAG,"MainActivity.onClassSelected: $classId")
        val fragment = SemesterClassListFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

}