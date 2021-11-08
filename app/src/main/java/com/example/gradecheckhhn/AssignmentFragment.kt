package com.example.gradecheckhhn

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.gradecheckhhn.databaseEntities.Assignment
import java.util.*

private const val TAG = "AssignmentFragment"
private const val ARG_ASSIGNMENT_ID = "assignment_id"

class AssignmentFragment : Fragment() {

    private lateinit var assignment: Assignment
    private lateinit var assignmentNameField: EditText

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
        assignmentNameField = view.findViewById(R.id.add_assignment_name) as EditText
        return view
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