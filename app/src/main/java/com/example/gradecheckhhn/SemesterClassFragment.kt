package com.example.gradecheckhhn

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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

    override fun onStart() {
        super.onStart()

        val titleMatcher = object : TextWatcher {

            override fun beforeTextChanged(
                sequence: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
                // This space intentionally left blank
            }

            override fun onTextChanged(
                sequence: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                SemesterClass.className = sequence.toString()
            }

            override fun afterTextChanged(sequence: Editable?) {
                // This one too
            }
        }

        courseNameField.addTextChangedListener(titleMatcher)

    }
}