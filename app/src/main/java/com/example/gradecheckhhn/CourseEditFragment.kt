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
import com.example.gradecheckhhn.databaseEntities.Course
import java.util.*

private const val ARG_COURSE_ID = "course_id"
private const val ARG_SEMESTER_ID = "semester_id"

class CourseEditFragment : Fragment() {

    private lateinit var course: Course

    private lateinit var courseName : EditText

    private lateinit var updateCourseButton : Button

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

    private val editCourseViewModel: CourseEditViewModel by lazy {
        ViewModelProviders.of(this).get(CourseEditViewModel::class.java)
    }

    private var callbacks: Callbacks? = null

    interface Callbacks {
        fun onUpdateCourseSelected(semesterId:UUID)
    }

    override fun onAttach(context : Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_edit_course,container,false)

        updateCourseButton = view.findViewById(R.id.update_class_button)
        courseName = view.findViewById(R.id.add_class_course_name) as EditText

        breakdownOne = view.findViewById(R.id.add_class_breakdown_item_1) as EditText
        weightOne = view.findViewById(R.id.add_class_breakdown_weight_1) as EditText

        breakdownTwo = view.findViewById(R.id.add_class_breakdown_item_2) as EditText
        weightTwo = view.findViewById(R.id.add_class_breakdown_weight_2) as EditText

        breakdownThree = view.findViewById(R.id.add_class_breakdown_item_3) as EditText
        weightThree = view.findViewById(R.id.add_class_breakdown_weight_3) as EditText

        breakdownFour = view.findViewById(R.id.add_class_breakdown_item_4) as EditText
        weightFour = view.findViewById(R.id.add_class_breakdown_weight_4) as EditText

        breakdownFive = view.findViewById(R.id.add_class_breakdown_item_5) as EditText
        weightFive = view.findViewById(R.id.add_class_breakdown_weight_5) as EditText

        weightSix = view.findViewById(R.id.add_class_breakdown_weight_6) as EditText

        minA = view.findViewById(R.id.add_class_grade_weight_A_min) as EditText
        maxA = view.findViewById(R.id.add_class_grade_weight_A_max) as EditText

        minB = view.findViewById(R.id.add_class_grade_weight_B_min) as EditText
        maxB = view.findViewById(R.id.add_class_grade_weight_B_max) as EditText

        minC = view.findViewById(R.id.add_class_grade_weight_C_min) as EditText
        maxC = view.findViewById(R.id.add_class_grade_weight_C_max) as EditText

        minD = view.findViewById(R.id.add_class_grade_weight_D_min) as EditText
        maxD = view.findViewById(R.id.add_class_grade_weight_D_max) as EditText

        minF = view.findViewById(R.id.add_class_grade_weight_F_min) as EditText
        maxF = view.findViewById(R.id.add_class_grade_weight_F_max) as EditText

        return view
    }

    companion object {
        fun newInstance(courseId: UUID,semesterId: UUID): CourseEditFragment {
            val args = Bundle().apply {
                putSerializable(ARG_COURSE_ID, courseId)
                putSerializable(ARG_SEMESTER_ID,semesterId)
            }
            return CourseEditFragment().apply {
                arguments = args
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val courseId = arguments?.getSerializable(ARG_COURSE_ID) as UUID
        editCourseViewModel.loadCourse(courseId)
        editCourseViewModel.courseLiveData.observe(
            viewLifecycleOwner,
            Observer { course ->
                course?.let {
                    this.course = course
                    updateUI()
                }
            }
        )
    }

    override fun onStart() {
        super.onStart()

        updateCourseButton.setOnClickListener{
            Toast.makeText(context,"Course Updated", Toast.LENGTH_SHORT)
                .show()
            course.courseName = courseName.text.toString()

            course.breakdown1Name = breakdownOne.text.toString()
            course.breakdown1Weight = weightOne.text.toString().toDouble()

            course.breakdown2Name = breakdownTwo.text.toString()
            course.breakdown2Weight = weightTwo.text.toString().toDouble()

            course.breakdown3Name = breakdownThree.text.toString()
            course.breakdown3Weight = weightThree.text.toString().toDouble()

            course.breakdown4Name = breakdownFour.text.toString()
            course.breakdown4Weight = weightFour.text.toString().toDouble()

            course.breakdown5Name = breakdownFive.text.toString()
            course.breakdown5Weight = weightFive.text.toString().toDouble()

            course.breakdown6Weight = weightSix.text.toString().toDouble()

            course.maxA = maxA.text.toString().toDouble()
            course.minA = minA.text.toString().toDouble()

            course.maxB = maxB.text.toString().toDouble()
            course.minB = minB.text.toString().toDouble()

            course.maxC = maxC.text.toString().toDouble()
            course.minC = minC.text.toString().toDouble()

            course.maxD = maxD.text.toString().toDouble()
            course.minD = minD.text.toString().toDouble()

            course.maxF = maxF.text.toString().toDouble()
            course.minF = minF.text.toString().toDouble()

            editCourseViewModel.updateCourse(course)

            val semesterId = arguments?.getSerializable(ARG_SEMESTER_ID) as UUID
            callbacks?.onUpdateCourseSelected(semesterId)
        }
    }

    //Sets edit text based on the info of the course selected
    private fun updateUI(){
        courseName.setText(course.courseName)

        breakdownOne.setText(course.breakdown1Name)
        weightOne.setText(course.breakdown1Weight.toString())

        breakdownTwo.setText(course.breakdown2Name)
        weightTwo.setText(course.breakdown2Weight.toString())

        breakdownThree.setText(course.breakdown3Name)
        weightThree.setText(course.breakdown3Weight.toString())

        breakdownFour.setText(course.breakdown4Name)
        weightFour.setText(course.breakdown4Weight.toString())

        breakdownFive.setText(course.breakdown5Name)
        weightFive.setText(course.breakdown5Weight.toString())

        weightSix.setText(course.breakdown6Weight.toString())

        minA.setText(course.minA.toString())
        maxA.setText(course.maxA.toString())

        minB.setText(course.minB.toString())
        maxB.setText(course.maxB.toString())

        minC.setText(course.minC.toString())
        maxC.setText(course.maxC.toString())

        minD.setText(course.minD.toString())
        maxD.setText(course.maxD.toString())

        minF.setText(course.minF.toString())
        maxF.setText(course.maxF.toString())
    }

}