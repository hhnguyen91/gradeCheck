package com.example.gradecheckhhn

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.gradecheckhhn.databaseEntities.Assignment
import com.example.gradecheckhhn.databaseEntities.Semester

class AddAssignment : Fragment(){
    interface Callbacks {
        fun onAddAssignment()
    }

    //private var callbacks: AddAssignment.Callbacks? = null
    private var callbacks: Callbacks? = null

    /*Characteristics listed inside of the add assignment list fragment
    * which is of type assignment
    * Consists of assignment points, maximum points, and an add assignment button*/
    private lateinit var assignment: Assignment
    private lateinit var assignmentName: EditText
    private lateinit var assignmentPoints: EditText
    private lateinit var assignmentMaxPoints: EditText
    private lateinit var addAssignmentButton: Button

    private val addAssignmentViewModel : AssignmentListViewModel by lazy{
        ViewModelProviders.of(this).get(AssignmentListViewModel::class.java)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as AddAssignment.Callbacks?
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

        // Enables add semester button after some text has been applied to both editText fields
        override fun onTextChanged(
            sequence: CharSequence?,
            start: Int,
            count: Int,
            after: Int
        ) {

            if (assignmentPoints.text.isNotEmpty() && assignmentMaxPoints.text.isNotEmpty() &&
                    assignmentName.text.isNotEmpty()) {
                addAssignmentButton.apply {
                    isEnabled = true
                }
            }

           //assignment.assignmentName = assignmentName.toString()
            //assignment.currentPoints = assignmentPoints.toString()
            //semester.year = yearInput.toString()
            //semester.season= seasonInput.toString()
        }

        override fun afterTextChanged(s: Editable?) {
            TODO("Not yet implemented")
        }
    }

    override fun onStart() {
        super.onStart()

        addAssignmentButton.setOnClickListener {
            Toast.makeText(context, "${assignment.assignmentName} Added!", Toast.LENGTH_SHORT)
                .show()
            addAssignmentViewModel.addAssignment(assignment)
            callbacks?.onAddAssignment()
        }
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    override fun onStop() {
        super.onStop()

    }

    }