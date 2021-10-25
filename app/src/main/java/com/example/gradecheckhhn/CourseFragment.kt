package com.example.gradecheckhhn

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import java.util.*

private const val TAG = "CourseFragment"
private const val ARG_COURSE_ID = "course_id"

class CourseFragment : Fragment() {

    private lateinit var course: Course
    private lateinit var courseNameField: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        course = Course()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_course,container,false)

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
                course.courseName = sequence.toString()
            }

            override fun afterTextChanged(sequence: Editable?) {
                // This one too
            }
        }

        courseNameField.addTextChangedListener(titleMatcher)

    }

    companion object {
        fun newInstance(courseId: UUID): CourseFragment {
            val args = Bundle().apply {
                putSerializable(ARG_COURSE_ID, courseId)
            }
            return CourseFragment().apply{
                arguments = args
            }
        }
    }
}