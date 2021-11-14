package com.example.gradecheckhhn

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.gradecheckhhn.databaseEntities.Assignment
import com.example.gradecheckhhn.databaseEntities.Course
import java.util.*

private const val TAG = "CourseFragment"
private const val ARG_COURSE_ID = "course_id"

class CourseFragment : Fragment() {

    private lateinit var course: Course
    //private lateinit var courseNameField: EditText

    private lateinit var courseTitle: TextView

    interface Callbacks {
        fun onEditCoursePressed(assignmentID: UUID, courseId: UUID, semesterId: UUID)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        course = Course()
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_course,container,false)

        courseTitle = view.findViewById(R.id.class_title)

        //courseNameField = view.findViewById(R.id.add_class_course_name) as EditText
        //courseTitle.text = "${course.courseName.uppercase()}"
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

        //courseNameField.addTextChangedListener(titleMatcher)

    }

    // Change the menu from adding course to assignment
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
           inflater.inflate(R.menu.fragment_assignment_list,menu)
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

    private inner class AssignmentHolder(view: View)
        :RecyclerView.ViewHolder(view), View.OnClickListener{
        private lateinit var assignment: Assignment

        private val editAssignmentButton: Button = itemView.findViewById(R.id.edit_assignment_Button)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {

        }
    }
}