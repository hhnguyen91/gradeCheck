package com.example.gradecheckhhn

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.gradecheckhhn.databaseEntities.Assignment
import com.example.gradecheckhhn.databaseEntities.Course
import com.example.gradecheckhhn.databaseEntities.Semester
import java.util.*

private const val TAG = "CourseFragment"
private const val ARG_COURSE_ID = "course_id"

class CourseFragment : Fragment() {

    private lateinit var semester: Semester
    private lateinit var course: Course
    //private lateinit var courseNameField: EditText

    private lateinit var courseTitle: TextView

    private var callbacks: Callbacks? = null

    interface Callbacks {
        fun onEditAssignmentPressed(assignmentID: UUID, courseId: UUID, semesterId: UUID)
        fun onAddAssignmentSelected(courseId: UUID, semesterId: UUID)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        semester = Semester()
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

        val courseId: UUID = arguments?.getSerializable(ARG_COURSE_ID) as UUID

        //courseNameField = view.findViewById(R.id.add_class_course_name) as EditText
        //courseTitle.text = "${course.courseName.uppercase()}"
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i(TAG,"Course: ${course.courseName} Selected")
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.new_assignment ->
            {
                Log.d(TAG,"Directing user to create assignment form")
                callbacks?.onAddAssignmentSelected(course.CourseID, semester.id)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    private inner class AssignmentHolder(view: View)
        :RecyclerView.ViewHolder(view), View.OnClickListener{
        private lateinit var assignment: Assignment

        private val editAssignmentButton: Button = itemView.findViewById(R.id.edit_assignment_Button)

        init {
            itemView.setOnClickListener(this)
            editAssignmentButton.setOnClickListener {
                callbacks?.onEditAssignmentPressed(assignment.AssignmentID, course.CourseID,semester.id)
            }
        }

        override fun onClick(v: View?) {

        }
    }

    private inner class AssignmentAdapter (var assignmentList: List<Assignment>)
        :RecyclerView.Adapter<AssignmentHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AssignmentHolder {
            TODO("Not yet implemented")
        }

        override fun onBindViewHolder(holder: AssignmentHolder, position: Int) {
            TODO("Not yet implemented")
        }

        override fun getItemCount() = assignmentList.size
    }

}