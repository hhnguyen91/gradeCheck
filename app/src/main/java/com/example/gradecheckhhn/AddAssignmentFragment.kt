package com.example.gradecheckhhn

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.gradecheckhhn.databaseEntities.Assignment
import com.example.gradecheckhhn.databaseEntities.Course
import java.io.Console
import java.util.*
import kotlin.collections.ArrayList

private const val TAG = "AddAssignmentFragment"
private const val ARG_SEMESTER_ID = "semester_id"
private const val ARG_COURSE_ID = "course_id"

class AddAssignmentFragment : Fragment(){


    interface Callbacks {
        fun onAddAssignmentButtonPressed()
    }

    //private var callbacks: AddAssignmentFragment.Callbacks? = null
    private var callbacks: Callbacks? = null

    /*Characteristics listed inside of the add assignment list fragment
    * which is of type assignment
    * Consists of assignment points, maximum points, and an add assignment button*/
    private lateinit var course: Course
    private lateinit var assignment: Assignment
    private lateinit var assignmentName: EditText
    private lateinit var assignmentPoints: EditText
    private lateinit var assignmentMaxPoints: EditText
    private lateinit var addAssignmentButton: Button
    private lateinit var breakDownSpinner: Spinner
    private lateinit var breakdownList : MutableList<String>

    private val addAssignmentViewModel : AddAssignmentViewModel by lazy{
        ViewModelProviders.of(this).get(AddAssignmentViewModel::class.java)
    }

    private val editCourseViewModel: CourseEditViewModel by lazy {
        ViewModelProviders.of(this).get(CourseEditViewModel::class.java)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as AddAssignmentFragment.Callbacks?
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_assignment,container,false)

        assignment = Assignment()

        addAssignmentButton = view.findViewById(R.id.add_assignment_button) as Button
        assignmentPoints = view.findViewById(R.id.add_assignment_current_points) as EditText
        assignmentMaxPoints = view.findViewById(R.id.add_assignment_maximum_points) as EditText
        assignmentName = view.findViewById(R.id.add_assignment_name) as EditText

        assignmentPoints.addTextChangedListener(textWatcher)
        assignmentMaxPoints.addTextChangedListener(textWatcher)
        assignmentName.addTextChangedListener(textWatcher)

        breakDownSpinner = view.findViewById(R.id.add_assignment_breakdown) as Spinner

        return view

    }

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(
            sequence: CharSequence?,
            start: Int,
            count: Int,
            after: Int
        ) {
        }

        // Enables add assignment button after some text has been applied to both editText fields
        override fun onTextChanged(
            sequence: CharSequence?,
            start: Int,
            count: Int,
            after: Int
        ) {
            val assignmentNameInput = assignmentName.text.trim()
            val assignmentCurrPointInput = assignmentPoints.text.trim()
            val assignmentMaxPointInput = assignmentMaxPoints.text.trim()

            if (assignmentCurrPointInput.isNotEmpty() && assignmentMaxPointInput.isNotEmpty() &&
                assignmentNameInput.isNotEmpty()) {
                addAssignmentButton.apply {
                    isEnabled = true
                }
                assignment.assignmentName = assignmentNameInput.toString()
                assignment.currentPoints = assignmentCurrPointInput.toString().toDouble()
                assignment.maximumPoints = assignmentMaxPointInput.toString().toDouble()
            }



        }

        override fun afterTextChanged(s: Editable?) {
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val courseId = arguments?.getSerializable(ARG_COURSE_ID) as UUID
        assignment.CourseID = courseId.toString()
        Log.i(TAG,"CourseID: $courseId")
        editCourseViewModel.loadCourse(courseId)
        editCourseViewModel.courseLiveData.observe(
            viewLifecycleOwner,
            Observer { course ->
                course?.let {
                    this.course = course
                    //Adding values to the assignment breakdown dropdown menu
                    breakdownList = ArrayList()
                    //Check if the breakdown is not empty, add to list if isn't
                    if(course.breakdown1Name != "") breakdownList.add(course.breakdown1Name)
                    if(course.breakdown2Name != "") breakdownList.add(course.breakdown2Name)
                    if(course.breakdown3Name != "") breakdownList.add(course.breakdown3Name)
                    if(course.breakdown4Name != "") breakdownList.add(course.breakdown4Name)
                    if(course.breakdown5Name != "") breakdownList.add(course.breakdown5Name)

                    breakDownSpinner.adapter = context?.let {
                        ArrayAdapter(
                            it,
                            R.layout.assignment_breakdown_row,
                            R.id.assignment_breakdown_row,
                            breakdownList
                        )
                    }
                }
            }
        )
        breakDownSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (parent != null) {
                    assignment.breakdownName = parent.getItemAtPosition(position).toString()
                    Log.i(TAG,"Breakdown: ${assignment.breakdownName}")
                }
            }

        }
    }

    override fun onStart() {
        super.onStart()

        addAssignmentButton.setOnClickListener {
            Toast.makeText(context, "${assignment.assignmentName} Added!", Toast.LENGTH_SHORT)
                .show()
            addAssignmentViewModel.addAssignment(assignment)
            callbacks?.onAddAssignmentButtonPressed()
        }
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    override fun onStop() {
        super.onStop()

    }

    companion object {
        fun newInstance(courseId: UUID,semesterId: UUID) : AddAssignmentFragment {
            var args = Bundle().apply {
                putSerializable(ARG_SEMESTER_ID,semesterId)
                putSerializable(ARG_COURSE_ID,courseId)
            }
            return AddAssignmentFragment().apply{
                arguments = args
            }
        }
    }
}