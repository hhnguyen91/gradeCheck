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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.gradecheckhhn.databaseEntities.Semester
import java.util.*

private const val ARG_SEMESTER_ID = "semester_id"

class SemesterEditFragment : Fragment() {

    private lateinit var semester: Semester
    private lateinit var semesterYear : EditText
    private lateinit var semesterSeason : EditText
    private lateinit var updateSemesterButton: Button

    private val semesterDetailViewModel: SemesterDetailViewModel by lazy {
        ViewModelProviders.of(this).get(SemesterDetailViewModel::class.java)
    }

    private val editSemesterViewModel: SemesterEditViewModel by lazy {
        ViewModelProviders.of(this).get(SemesterEditViewModel::class.java)
    }

    private var callbacks: Callbacks? = null

    interface Callbacks {
        fun onUpdateSemesterSelected()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_semester_edit, container, false)
        semesterYear = view.findViewById(R.id.edit_semester_year) as EditText
        semesterSeason = view.findViewById(R.id.edit_semester_season) as EditText
        updateSemesterButton = view.findViewById(R.id.update_semester_button) as Button
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val semesterId = arguments?.getSerializable(ARG_SEMESTER_ID) as UUID
        semesterDetailViewModel.loadSemester(semesterId)
        semesterDetailViewModel.semesterLiveData.observe(
            viewLifecycleOwner,
            Observer { semester ->
                semester?.let {
                    this.semester = semester
                    updateUI()
                }
            }
        )
    }

    companion object {
        fun newInstance(semesterId: UUID): SemesterEditFragment {
            val args = Bundle().apply {
                putSerializable(ARG_SEMESTER_ID, semesterId)
            }
            return SemesterEditFragment().apply {
                arguments = args
            }
        }
    }

    override fun onStart() {
        super.onStart()

        updateSemesterButton.setOnClickListener{
            Toast.makeText(context,"Semester Updated", Toast.LENGTH_SHORT)
                .show()
            semester.season = semesterSeason.text.toString()
            semester.year = semesterYear.text.toString()
            editSemesterViewModel.updateSemester(semester)
            callbacks?.onUpdateSemesterSelected()
        }
    }

    //Sets edit text based on the info of the semester selected
    private fun updateUI(){
        semesterYear.setText(semester.year)
        semesterSeason.setText(semester.season)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as SemesterEditFragment.Callbacks?
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

}