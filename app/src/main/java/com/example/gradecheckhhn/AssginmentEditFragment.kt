package com.example.gradecheckhhn

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.gradecheckhhn.databaseEntities.Assignment
import java.util.*

private const val ARG_ASSIGNMENT_ID = "assignment_id"

// Don't call this fragment until add Assignment fragment is completed
class AssginmentEditFragment : Fragment() {
    private lateinit var assignment :Assignment

    private lateinit var assignmentName : EditText
    private lateinit var breakdownName : EditText
    private lateinit var currentPoints : EditText
    private lateinit var maximumPoints : EditText

    private lateinit var updateAssignmentButton: Button

    /*
    private val editCourseViewModel: AssignmentEditViewModel by lazy {
        ViewModelProviders.of(this).get(AssignmentEditViewModel::class.java)
    }
    */

    private var callbacks: Callbacks? = null

    interface Callbacks {
        fun onUpdateAssignmentSelected()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //val view = inflater.inflate(R.layout.fragment_assignment_edit, container,false)

        //assignmentName = view.findViewById(R.id.edit_assignment_name) as EditText
        //breakdownName = view.findViewById(R.id.edit_assignment_breakdown_name) as EditText
        //currentPoints = view.findViewById(R.id.edit_assignment_current_points) as EditText
        //maximumPoints = view.findViewById(R.id.edit_assignment_max_points) as EditText

        //updateAssignmentButton = view.findViewById(R.id.update_assignment_button) as Button

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    companion object {
        fun newInstance(assignmentID : UUID): AssginmentEditFragment {
        val args = Bundle().apply {
            putSerializable(ARG_ASSIGNMENT_ID,assignmentID)
        }
            return AssginmentEditFragment().apply{
                arguments = args
            }
        }
    }

    override fun onStart() {
        super.onStart()

        updateAssignmentButton.setOnClickListener{
            Toast.makeText(context,"Assignment Updated", Toast.LENGTH_SHORT)
                .show()
            assignment.assignmentName = assignmentName.text.toString()

        }
    }

    private fun updateUI()
    {
        assignmentName.setText(assignment.assignmentName)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as AssginmentEditFragment.Callbacks?
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

}

