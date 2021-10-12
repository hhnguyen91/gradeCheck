package com.example.gradecheckhhn

import android.app.Activity
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
import androidx.lifecycle.ViewModelProviders
import javax.security.auth.callback.Callback

class AddSemesterFragment : Fragment() {

    interface Callbacks {
        fun onAddSemesterButtonClicked()
    }

    private var callbacks: Callbacks? = null

    private lateinit var semester: Semester
    private lateinit var addSemesterButton: Button
    private lateinit var semesterSeason: EditText
    private lateinit var semesterYear: EditText
    private val addSemesterViewModel: AddSemesterViewModel by lazy {
        ViewModelProviders.of(this).get(AddSemesterViewModel::class.java)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_semester,container,false)

        semester = Semester()

        addSemesterButton = view.findViewById(R.id.add_semester_button) as Button
        semesterSeason = view.findViewById(R.id.add_semester_season) as EditText
        semesterYear = view.findViewById(R.id.add_semester_year) as EditText

        semesterYear.addTextChangedListener(textWatcher)
        semesterSeason.addTextChangedListener(textWatcher)

        return view

    }

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(  sequence: CharSequence?,
                                         start: Int,
                                         count: Int,
                                         after: Int) {
        }

        // Enables add semester button after some text has been applied to both editText fields
        override fun onTextChanged(  sequence: CharSequence?,
                                     start: Int,
                                     count: Int,
                                     after: Int) {
            val seasonInput = semesterSeason.text.trim()
            val yearInput = semesterYear.text.trim()

            if(seasonInput.isNotEmpty() && yearInput.isNotEmpty()) {
                addSemesterButton.apply {
                    isEnabled = true
                }
            }

            semester.year = yearInput.toString()
            semester.season= seasonInput.toString()
        }

        override fun afterTextChanged(sequence: Editable?) {
        }

    }

    override fun onStart() {
        super.onStart()

        addSemesterButton.setOnClickListener {
            Toast.makeText(context, "${semester.season} ${semester.year} Added!", Toast.LENGTH_SHORT)
                .show()
            addSemesterViewModel.addSemester(semester)
            callbacks?.onAddSemesterButtonClicked()
        }
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    override fun onStop() {
        super.onStop()
        //addSemesterViewModel.addSemester(semester)
    }


}


