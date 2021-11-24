package com.example.gradecheckhhn

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.gradecheckhhn.databaseEntities.Assignment
import com.example.gradecheckhhn.databaseEntities.Course
import java.util.*

private const val ARG_ASSIGNMENT_ID = "assignment_id"
private const val ARG_COURSE_ID = "course_id"
private const val ARG_SEMESTER_ID = "semester_id"

// Don't call this fragment until add Assignment fragment is completed
class AssignmentEditFragment : Fragment() {
    private lateinit var assignment :Assignment
    private lateinit var course: Course

    private lateinit var assignmentName : EditText
    private lateinit var breakdownSpinner : Spinner
    private lateinit var currentPoints : EditText
    private lateinit var maximumPoints : EditText

    private lateinit var breakdownList : MutableList<String>

    private lateinit var updateAssignmentButton: Button


    private val editAssignmentViewModel: AssignmentEditViewModel by lazy {
        ViewModelProviders.of(this).get(AssignmentEditViewModel::class.java)
    }

    private val editCourseViewModel: CourseEditViewModel by lazy {
        ViewModelProviders.of(this).get(CourseEditViewModel::class.java)
    }

    private var callbacks: Callbacks? = null

    interface Callbacks {
        fun onUpdateAssignmentSelected(courseID: UUID)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_edit_assignment, container,false)

        assignmentName = view.findViewById(R.id.edit_assignment_name) as EditText
        breakdownSpinner = view.findViewById(R.id.edit_assignment_breakdown) as Spinner
        currentPoints = view.findViewById(R.id.edit_assignment_current_points) as EditText
        maximumPoints = view.findViewById(R.id.edit_assignment_maximum_points) as EditText

        updateAssignmentButton = view.findViewById(R.id.update_assignment_button) as Button

        return view
    }

    companion object {
        fun newInstance(assignmentID : UUID,courseID: UUID, semesterID:UUID): AssignmentEditFragment {
        val args = Bundle().apply {
            putSerializable(ARG_ASSIGNMENT_ID,assignmentID)
            putSerializable(ARG_COURSE_ID, courseID)
            putSerializable(ARG_SEMESTER_ID,semesterID)
        }
            return AssignmentEditFragment().apply{
                arguments = args
            }
        }
    }

    override fun onStart() {
        super.onStart()

        updateAssignmentButton.setOnClickListener{
            assignment.assignmentName = assignmentName.text.toString()
            assignment.breakdownName = breakdownSpinner.selectedItem.toString()
            assignment.currentPoints = currentPoints.text.toString().toDouble()
            assignment.maximumPoints = maximumPoints.text.toString().toDouble()

            editAssignmentViewModel.updateAssignment(assignment)

            Toast.makeText(context,"Assignment Updated", Toast.LENGTH_SHORT)
                .show()

            val courseId = arguments?.getSerializable(ARG_COURSE_ID) as UUID
            callbacks?.onUpdateAssignmentSelected(courseId)
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val assignmentId = arguments?.getSerializable(ARG_ASSIGNMENT_ID) as UUID
        val courseId = arguments?.getSerializable(ARG_COURSE_ID) as UUID

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

                    breakdownSpinner.adapter = context?.let {
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

        editAssignmentViewModel.loadAssignment(assignmentId)
        editAssignmentViewModel.assignmentLiveData.observe(
            viewLifecycleOwner,
            Observer { assignment ->
                assignment?.let {
                    this.assignment = assignment

                    updateUI()
                }
            }
        )
    }

    private fun updateUI()
    {
        assignmentName.setText(assignment.assignmentName)
        currentPoints.setText(assignment.currentPoints.toString())
        maximumPoints.setText(assignment.maximumPoints.toString())

        // Set the dropdrown menu to a specific value
        val select : Int? = (breakdownSpinner.adapter as ArrayAdapter<String>?)?.getPosition("${assignment.breakdownName}")
        select?.let { it1 -> breakdownSpinner.setSelection(it1) }
    }
}

