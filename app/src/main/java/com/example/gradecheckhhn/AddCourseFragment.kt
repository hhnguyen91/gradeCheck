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
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProviders
import com.example.gradecheckhhn.databaseEntities.Course
import com.example.gradecheckhhn.databaseEntities.Semester
import java.util.*

private const val ARG_SEMESTER_ID = "semester_id"

class AddCourseFragment : Fragment() {

    private val semesterIdLiveData = MutableLiveData<UUID>()

    interface Callbacks {
         fun onAddCourseButtonClicked()

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

        val semesterId: UUID = arguments?.getSerializable(ARG_SEMESTER_ID) as UUID

        course = Course()

        addCourseButton = view.findViewById(R.id.add_class_button)
        courseName = view.findViewById(R.id.add_class_course_name) as EditText

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val semesterId = arguments?.getSerializable(ARG_SEMESTER_ID) as UUID
        course.SemesterID = semesterId.toString()
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

            //Getting all the input from the user
            val courseNameInput = courseName.text.trim()

            val breakdownOneInput = breakdownOne.text.trim()
            val weightOneInput = weightOne.text.trim()

            val breakdownTwoInput = breakdownTwo.text.trim()
            val weightTwoInput = weightTwo.text.trim()

            val breakdownThreeInput = breakdownThree.text.trim()
            val weightThreeInput = weightThree.text.trim()

            val breakdownFourInput = breakdownFour.text.trim()
            val weightFourInput = weightFour.text.trim()

            val breakdownFiveInput = breakdownFive.text.trim()
            val weightFiveInput = weightFive.text.trim()

            val weightSixInput = weightSix.text.trim()

            //Testing if the input field is blank, if not, assigning the value to the course
            if(courseNameInput.isNotEmpty()) {
                addCourseButton.apply{
                    isEnabled = true
                }
                course.courseName = courseNameInput.toString()
            }

            //Break down 1
            if(breakdownOneInput.isNotEmpty()) {
                course.breakdown1Name = breakdownOneInput.toString()
            }
            if(weightOneInput.isNotEmpty() && weightOneInput.toString().toDoubleOrNull() != null) {
                course.breakdown1Weight = weightOneInput.toString().toDouble()
            }

            //Break down 2
            if(breakdownTwoInput.isNotEmpty()) {
                course.breakdown2Name = breakdownTwoInput.toString()
            }
            if(weightTwoInput.isNotEmpty() && weightTwoInput.toString().toDoubleOrNull() != null) {
                course.breakdown2Weight = weightTwoInput.toString().toDouble()
            }

            //Break down 3
            if(breakdownThreeInput.isNotEmpty()) {
                course.breakdown3Name = breakdownThreeInput.toString()
            }
            if(weightThreeInput.isNotEmpty() && weightThreeInput.toString().toDoubleOrNull() != null) {
                course.breakdown3Weight = weightThreeInput.toString().toDouble()
            }

            //Break down 4
            if(breakdownFourInput.isNotEmpty()) {
                course.breakdown4Name = breakdownFourInput.toString()
            }
            if(weightTwoInput.isNotEmpty() && weightFourInput.toString().toDoubleOrNull() != null) {
                course.breakdown4Weight = weightFourInput.toString().toDouble()
            }

            //Break down 5
            if(breakdownFiveInput.isNotEmpty()) {
                course.breakdown5Name = breakdownFiveInput.toString()
            }
            if(weightFiveInput.isNotEmpty() && weightFiveInput.toString().toDoubleOrNull() != null) {
                course.breakdown5Weight = weightFiveInput.toString().toDouble()
            }

            //Break down 6
            if(weightSixInput.isNotEmpty() && weightSixInput.toString().toDoubleOrNull() != null) {
                course.breakdown6Weight = weightSixInput.toString().toDouble()
            }

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

    companion object {
        fun newInstance(semesterId: UUID) : AddCourseFragment{
            val args = Bundle().apply {
                putSerializable(ARG_SEMESTER_ID, semesterId)
            }
            return AddCourseFragment().apply {
                arguments = args
            }
        }
    }
}