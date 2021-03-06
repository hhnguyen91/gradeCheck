package com.example.gradecheckhhn

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.gradecheckhhn.databaseEntities.Assignment
import java.util.*

private const val TAG = "AssignmentFragment"
private const val ARG_ASSIGNMENT_ID = "assignment_id"

class AssignmentFragment : Fragment() {

    private var callbacks: Callbacks? = null

    private lateinit var assignment: Assignment
    private lateinit var assignmentNameField: EditText

    interface Callbacks {
        //fun onCourseSelected(courseId: UUID)
        //Should Direct user to create course Screen
        //fun onAddCourseSelected(semesterId: UUID)
        //fun onEditCoursePressed(courseId: UUID, semesterId: UUID)
        fun onAddAssignment(courseID: UUID)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        assignment = Assignment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_assignment, container, false)
        //assignmentNameField = view.findViewById(R.id.add_assignment_name) as EditText
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.fragment_assignment_list,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.new_course ->
            {

                Log.d(TAG,"Directing user to create assignment form")
                //callbacks?.onAddAssignment(assignment.AssignmentID)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {
        fun newInstance(assignmentId: UUID): AssignmentFragment {
            val args = Bundle().apply {
                putSerializable(ARG_ASSIGNMENT_ID, assignmentId)
            }
            return AssignmentFragment().apply{
                arguments = args
            }
        }
    }
}