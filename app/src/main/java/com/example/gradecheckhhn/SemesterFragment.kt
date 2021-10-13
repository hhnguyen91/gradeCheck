package com.example.gradecheckhhn

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.Observer
import java.util.*

private const val TAG = "SemesterFragment"
private const val ARG_SEMESTER_ID = "semester_id"

class SemesterFragment : Fragment() {

    private lateinit var semester: Semester
    private lateinit var semesterTitle: TextView

    private val semesterDetailViewModel: SemesterDetailViewModel by lazy {
        ViewModelProviders.of(this).get(SemesterDetailViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        semester = Semester()
        val semesterId: UUID = arguments?.getSerializable(ARG_SEMESTER_ID) as UUID
        Log.i(TAG,"Semester $semesterId selected")
        semesterDetailViewModel.loadSemester(semesterId)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_semester, container, false)
        semesterTitle = view.findViewById(R.id.semester_title) as TextView

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
                    Log.i(TAG,"Semester ${semester.season} ${semester.year} selected")
                    updateUI()
                }
            }
        )
    }

    private fun updateUI() {
        semesterTitle.text = "${semester.season.uppercase()} ${semester.year}"

    }

    companion object {
        fun newInstance(semesterId: UUID): SemesterFragment {
            val args = Bundle().apply {
                putSerializable(ARG_SEMESTER_ID, semesterId)
            }
            return SemesterFragment().apply {
                arguments = args
            }
        }
    }
}