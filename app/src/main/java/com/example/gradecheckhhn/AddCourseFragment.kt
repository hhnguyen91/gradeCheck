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
import androidx.lifecycle.ViewModelProviders
import java.util.*

class AddCourseFragment : Fragment() {

    interface Callbacks {
         fun onAddCourseButtonClicked(semesterId: UUID)
    }

    private var callbacks: Callbacks? = null

    private lateinit var course: Course
    // Add Class Button
    private lateinit var addCourseButton : Button
    private lateinit var courseName : EditText
    // Grade Break Down
    private lateinit var breakdownOne : EditText
    private lateinit var weightOne : EditText

    private lateinit var breakdownTwo : EditText
    private lateinit var weightTwo : EditText

    private lateinit var breakdownThree : EditText
    private lateinit var weightThree : EditText

    private lateinit var breakdownFour : EditText
    private lateinit var weightFour : EditText

    private lateinit var breakdownFive : EditText
    private lateinit var weightFive : EditText

    //private lateinit var breakdownSix : EditText
    private lateinit var weightSix : EditText
    // Grades
    // A
    private lateinit var maxA : EditText
    private lateinit var minA : EditText
    // B
    private lateinit var maxB : EditText
    private lateinit var minB : EditText
    // C
    private lateinit var maxC : EditText
    private lateinit var minC : EditText
    // D
    private lateinit var maxD : EditText
    private lateinit var minD : EditText
    // F
    private lateinit var maxF : EditText
    private lateinit var minF : EditText

    private val addCourseViewModel : AddCourseViewModel by lazy {
        ViewModelProviders.of(this).get(AddCourseViewModel::class.java)
    }

    override fun onAttach(context : Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_course,container,false)

        course = Course()

        addCourseButton = view.findViewById(R.id.add_class_button)
        courseName = view.findViewById(R.id.add_class_course_name)

        breakdownOne = view.findViewById(R.id.add_class_breakdown_item_1)
        weightOne = view.findViewById(R.id.add_class_breakdown_weight_1)

        breakdownTwo = view.findViewById(R.id.add_class_breakdown_item_2)
        weightTwo = view.findViewById(R.id.add_class_breakdown_weight_2)

        breakdownThree = view.findViewById(R.id.add_class_breakdown_item_3)
        weightThree = view.findViewById(R.id.add_class_breakdown_weight_3)

        breakdownFour = view.findViewById(R.id.add_class_breakdown_item_4)
        weightFour = view.findViewById(R.id.add_class_breakdown_weight_4)

        breakdownFive = view.findViewById(R.id.add_class_breakdown_item_5)
        weightFive = view.findViewById(R.id.add_class_breakdown_weight_5)

        //breakdownSix = view.findViewById(R.id.add_class_breakdown_item_6)
        weightSix = view.findViewById(R.id.add_class_breakdown_weight_6)

        minA = view.findViewById(R.id.add_class_grade_weight_A_min)
        maxA = view.findViewById(R.id.add_class_grade_weight_A_max)

        minB = view.findViewById(R.id.add_class_grade_weight_B_min)
        maxB = view.findViewById(R.id.add_class_grade_weight_B_max)

        minC = view.findViewById(R.id.add_class_grade_weight_C_min)
        maxC = view.findViewById(R.id.add_class_grade_weight_C_max)

        minD = view.findViewById(R.id.add_class_grade_weight_D_min)
        maxD = view.findViewById(R.id.add_class_grade_weight_D_max)

        minF = view.findViewById(R.id.add_class_grade_weight_F_min)
        maxF = view.findViewById(R.id.add_class_grade_weight_F_max)

        courseName.addTextChangedListener(textWatcher)

        breakdownOne.addTextChangedListener(textWatcher)
        weightOne.addTextChangedListener(textWatcher)

        breakdownTwo.addTextChangedListener(textWatcher)
        weightTwo.addTextChangedListener(textWatcher)

        breakdownThree.addTextChangedListener(textWatcher)
        weightThree.addTextChangedListener(textWatcher)

        breakdownFour.addTextChangedListener(textWatcher)
        weightFour.addTextChangedListener(textWatcher)

        breakdownFive.addTextChangedListener(textWatcher)
        weightFive.addTextChangedListener(textWatcher)

        //breakdownSix.addTextChangedListener(textWatcher)
        weightSix.addTextChangedListener(textWatcher)

        minA.addTextChangedListener(textWatcher)
        maxA.addTextChangedListener(textWatcher)

        minB.addTextChangedListener(textWatcher)
        maxB.addTextChangedListener(textWatcher)

        minC.addTextChangedListener(textWatcher)
        maxC.addTextChangedListener(textWatcher)

        minD.addTextChangedListener(textWatcher)
        maxD.addTextChangedListener(textWatcher)

        minF.addTextChangedListener(textWatcher)
        maxF.addTextChangedListener(textWatcher)

        return view
    }

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?,
                                       start: Int,
                                       count: Int,
                                       after: Int) {

        }

        override fun onTextChanged(s: CharSequence?,
                                   start: Int,
                                   before: Int,
                                   count: Int) {
            val courseNameInput = courseName.text.trim()

            if(courseNameInput.isNotEmpty()) {
                addCourseButton.apply{
                    isEnabled = true
                }
            }
        //Incomplete
        //Incomplete
        }

        override fun afterTextChanged(s: Editable?) {
        }
    }
    override fun onStart(){
        super.onStart()

        addCourseButton.setOnClickListener{
            Toast.makeText(context,"" +
                    "${course.courseName} \n " +
                    "Added!", Toast.LENGTH_SHORT).show()
            addCourseViewModel.addCourse(course)
            callbacks?.onAddCourseButtonClicked()
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