package com.example.gradecheckhhn

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment

class SemesterClassFragment : Fragment() {
    private lateinit var SemesterClass: SemesterClass

    private lateinit var courseNameField: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SemesterClass = SemesterClass()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_class,container,false)

        courseNameField = view.findViewById(R.id.add_class_course_name) as EditText

        return view
    }

    // TODO : Start working based on Page 163 in the android book
}