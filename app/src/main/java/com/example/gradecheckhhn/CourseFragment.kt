package com.example.gradecheckhhn

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gradecheckhhn.databaseEntities.Assignment
import com.example.gradecheckhhn.databaseEntities.Course
import com.example.gradecheckhhn.databaseEntities.Semester
import com.example.gradecheckhhn.databaseEntities.relationship.CourseWithManyAssignments
import org.w3c.dom.Text
import java.util.*
import kotlin.collections.ArrayList

private const val TAG = "CourseFragment"
private const val ARG_COURSE_ID = "course_id"

class CourseFragment : Fragment() {

    private lateinit var semester: Semester
    private lateinit var course: Course

    private lateinit var courseTitle: TextView
    private lateinit var courseGrade: TextView

    private lateinit var breakdownOneSection : LinearLayout
    private lateinit var breakdownTwoSection : LinearLayout
    private lateinit var breakdownThreeSection : LinearLayout
    private lateinit var breakdownFourSection : LinearLayout
    private lateinit var breakdownFiveSection : LinearLayout

    private lateinit var breakdownOneTitle: TextView
    private lateinit var breakdownTwoTitle: TextView
    private lateinit var breakdownThreeTitle: TextView
    private lateinit var breakdownFourTitle: TextView
    private lateinit var breakdownFiveTitle: TextView

    private lateinit var assignmentRecyclerViewBreakdownOne: RecyclerView
    private lateinit var assignmentRecyclerViewBreakdownTwo: RecyclerView
    private lateinit var assignmentRecyclerViewBreakdownThree: RecyclerView
    private lateinit var assignmentRecyclerViewBreakdownFour: RecyclerView
    private lateinit var assignmentRecyclerViewBreakdownFive: RecyclerView
    private lateinit var assignmentRecyclerViewBreakdownFinal: RecyclerView

    private lateinit var projectionforA: TextView
    private lateinit var projectionforB: TextView
    private lateinit var projectionforC: TextView

    private var adapter: AssignmentAdapter = AssignmentAdapter(emptyList())

    private val editCourseViewModel: CourseEditViewModel by lazy {
        ViewModelProviders.of(this).get(CourseEditViewModel::class.java)
    }

    private lateinit var assignmentListViewModel: AssignmentListViewModel
    private lateinit var assignmentListViewModelFactory: AssignmentListViewModelFactory

    private var callbacks: Callbacks? = null

    interface Callbacks {
        fun onEditAssignmentPressed(assignmentID: UUID, courseId: UUID, semesterId: UUID)
        fun onAddAssignmentSelected(courseId: UUID, semesterId: UUID)
        fun refreshAssignmentPage()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        semester = Semester()
        val courseId = arguments?.getSerializable(ARG_COURSE_ID) as UUID
        assignmentListViewModelFactory = AssignmentListViewModelFactory(courseId)
        assignmentListViewModel = ViewModelProvider(this, assignmentListViewModelFactory).get(AssignmentListViewModel::class.java)
        setHasOptionsMenu(true)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_course,container,false)

        breakdownOneSection = view.findViewById(R.id.Course_breakdown_One_Section)
        breakdownTwoSection = view.findViewById(R.id.Course_breakdown_Two_Section)
        breakdownThreeSection = view.findViewById(R.id.Course_breakdown_Three_Section)
        breakdownFourSection = view.findViewById(R.id.Course_breakdown_Four_Section)
        breakdownFiveSection = view.findViewById(R.id.Course_breakdown_Five_Section)

        breakdownOneTitle = view.findViewById(R.id.Course_Breakdown_Label_One)
        breakdownTwoTitle = view.findViewById(R.id.Course_Breakdown_Label_Two)
        breakdownThreeTitle = view.findViewById(R.id.Course_Breakdown_Label_Three)
        breakdownFourTitle = view.findViewById(R.id.Course_Breakdown_Label_Four)
        breakdownFiveTitle = view.findViewById(R.id.Course_Breakdown_Label_Five)

        courseTitle = view.findViewById(R.id.class_title)
        courseGrade = view.findViewById(R.id.class_grade)

        assignmentRecyclerViewBreakdownOne = view.findViewById(R.id.class_breakdown_One)
        assignmentRecyclerViewBreakdownOne.layoutManager = LinearLayoutManager(context)
        assignmentRecyclerViewBreakdownOne.adapter = adapter

        assignmentRecyclerViewBreakdownTwo = view.findViewById(R.id.class_breakdown_Two)
        assignmentRecyclerViewBreakdownTwo.layoutManager = LinearLayoutManager(context)
        assignmentRecyclerViewBreakdownTwo.adapter = adapter

        assignmentRecyclerViewBreakdownThree = view.findViewById(R.id.class_breakdown_Three)
        assignmentRecyclerViewBreakdownThree.layoutManager = LinearLayoutManager(context)
        assignmentRecyclerViewBreakdownThree.adapter = adapter

        assignmentRecyclerViewBreakdownFour = view.findViewById(R.id.class_breakdown_Four)
        assignmentRecyclerViewBreakdownFour.layoutManager = LinearLayoutManager(context)
        assignmentRecyclerViewBreakdownFour.adapter = adapter

        assignmentRecyclerViewBreakdownFive = view.findViewById(R.id.class_breakdown_Five)
        assignmentRecyclerViewBreakdownFive.layoutManager = LinearLayoutManager(context)
        assignmentRecyclerViewBreakdownFive.adapter = adapter

        assignmentRecyclerViewBreakdownFinal = view.findViewById(R.id.class_breakdown_Final)
        assignmentRecyclerViewBreakdownFinal.layoutManager = LinearLayoutManager(context)
        assignmentRecyclerViewBreakdownFive.adapter = adapter

        projectionforA = view.findViewById(R.id.projection_for_A)
        projectionforB = view.findViewById(R.id.projection_for_B)
        projectionforC = view.findViewById(R.id.projection_for_C)

        val courseId = arguments?.getSerializable(ARG_COURSE_ID) as UUID
        Log.i(TAG,"Course: $courseId Selected")
        course = Course()
        editCourseViewModel.loadCourse(courseId)
        editCourseViewModel.courseLiveData.observe(
            viewLifecycleOwner,
            Observer { course ->
                course?.let {
                    this.course = course
                    Log.i(TAG,"Course breakdown1 weight observer: ${course.breakdown1Weight}")
                    updateUI(course)
                }
            }
        )

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


    override fun onStart() {
        super.onStart()

        val titleMatcher = object : TextWatcher {

            override fun beforeTextChanged(
                sequence: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
                // This space intentionally left blank
            }

            override fun onTextChanged(
                sequence: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                course.courseName = sequence.toString()
            }

            override fun afterTextChanged(sequence: Editable?) {
                // This one too
            }
        }

        assignmentListViewModel.assignmentListLiveData.observe(
            viewLifecycleOwner,
            Observer { assignments->
                assignments?.let {
                    showAssignments(assignments)
                    showGrade(assignments)
                }
            }
        )


    }

    private fun showGrade(assignments: List<CourseWithManyAssignments>) {
        val listOfAllAssignments : List<Assignment> = assignments[0].assignmentList

        var totalWeight : Double = 0.0

        val breakDownOneList : MutableList<Assignment> = ArrayList()
        val breakDownOneWeight: Double = course.breakdown1Weight

        val breakDownTwoList : MutableList<Assignment> = ArrayList()
        val breakDownTwoWeight: Double = course.breakdown2Weight

        val breakDownThreeList : MutableList<Assignment> = ArrayList()
        val breakDownThreeWeight: Double = course.breakdown3Weight

        val breakDownFourList : MutableList<Assignment> = ArrayList()
        val breakDownFourWeight: Double = course.breakdown4Weight

        val breakDownFiveList : MutableList<Assignment> = ArrayList()
        val breakDownFiveWeight: Double = course.breakdown5Weight

        val breakDownFinalList : MutableList<Assignment> = ArrayList()
        val breakDownFinalWeight : Double = course.breakdown6Weight

        for (assignment in listOfAllAssignments) {
            if(assignment.breakdownName == breakdownOneTitle.text){
                breakDownOneList.add(assignment)
            }
            if(assignment.breakdownName == breakdownTwoTitle.text){
                breakDownTwoList.add(assignment)
            }
            if(assignment.breakdownName == breakdownThreeTitle.text){
                breakDownThreeList.add(assignment)
            }
            if(assignment.breakdownName == breakdownFourTitle.text){
                breakDownFourList.add(assignment)
            }
            if(assignment.breakdownName == breakdownFiveTitle.text){
                breakDownFiveList.add(assignment)
            }
            if(assignment.breakdownName == "Final"){
                breakDownFinalList.add(assignment)
            }
        }

        var breakdownOneGrade : Double = 0.0
        var breakdownTwoGrade : Double = 0.0
        var breakdownThreeGrade : Double = 0.0
        var breakdownFourGrade : Double = 0.0
        var breakdownFiveGrade : Double = 0.0
        var breakdownFinalGrade : Double = 0.0

        var currentPoints : Double = 0.0
        var maxPoints: Double = 0.0

        if(breakDownOneList.size > 0) {
            totalWeight += breakDownOneWeight
            for (assignment in breakDownOneList) {
                currentPoints += assignment.currentPoints
                maxPoints += assignment.maximumPoints
            }
            breakdownOneGrade = (currentPoints / maxPoints) * breakDownOneWeight
        }
        currentPoints = 0.0
        maxPoints = 0.0

        if(breakDownTwoList.size > 0) {
            totalWeight += breakDownTwoWeight
            for (assignment in breakDownTwoList) {
                currentPoints += assignment.currentPoints
                maxPoints += assignment.maximumPoints
            }
            breakdownTwoGrade = (currentPoints / maxPoints) * breakDownTwoWeight
        }
        currentPoints = 0.0
        maxPoints = 0.0

        if(breakDownThreeList.size > 0) {
            totalWeight += breakDownThreeWeight
            for (assignment in breakDownThreeList) {
                currentPoints += assignment.currentPoints
                maxPoints += assignment.maximumPoints
            }
            breakdownThreeGrade = (currentPoints / maxPoints) * breakDownThreeWeight
        }
        currentPoints = 0.0
        maxPoints = 0.0

        if(breakDownFourList.size > 0) {
            totalWeight += breakDownFourWeight
            for (assignment in breakDownFourList) {
                currentPoints += assignment.currentPoints
                maxPoints += assignment.maximumPoints
            }
            breakdownFourGrade = (currentPoints / maxPoints) * breakDownFourWeight
        }
        currentPoints = 0.0
        maxPoints = 0.0

        if(breakDownFiveList.size > 0) {
            totalWeight += breakDownFiveWeight
            for (assignment in breakDownFiveList) {
                currentPoints += assignment.currentPoints
                maxPoints += assignment.maximumPoints
            }
            breakdownFiveGrade = (currentPoints / maxPoints) * breakDownFiveWeight
        }
        currentPoints = 0.0
        maxPoints = 0.0

        if(breakDownFinalList.size > 0) {
            totalWeight += breakDownFinalWeight
            for (assignment in breakDownFinalList) {
                currentPoints += assignment.currentPoints
                maxPoints += assignment.maximumPoints
            }
            breakdownFinalGrade = (currentPoints / maxPoints) * breakDownFinalWeight
        }

        if(breakDownOneList.isNotEmpty() || breakDownTwoList.isNotEmpty() || breakDownThreeList.isNotEmpty() || breakDownFourList.isNotEmpty() || breakDownFiveList.isNotEmpty())
        {
            var currentGrade = breakdownOneGrade + breakdownTwoGrade + breakdownThreeGrade + breakdownFourGrade + breakdownFiveGrade
            //What grade do I need
            var neededGradeForA = ((course.minA - currentGrade) / breakDownFinalWeight) * 100
            var neededGradeForB = ((course.minB - currentGrade) / breakDownFinalWeight) * 100
            var neededGradeForC = ((course.minC - currentGrade) / breakDownFinalWeight) * 100

            projectionforA.text = String.format("%.2f", neededGradeForA) + "%"
            projectionforB.text = String.format("%.2f", neededGradeForB) + "%"
            projectionforC.text = String.format("%.2f", neededGradeForC) + "%"

        }
        // they already took the final so they don't need to guess what
        // they need to know what they need to get on the final to get their final grade
        if(breakDownFinalList.isNotEmpty())
        {
            projectionforA.text = "N/A"
            projectionforB.text = "N/A"
            projectionforC.text = "N/A"
        }

        var grade = ((breakdownOneGrade + breakdownTwoGrade + breakdownThreeGrade + breakdownFourGrade + breakdownFiveGrade + breakdownFinalGrade)/totalWeight) * 100
        val rounded = String.format("%.2f", grade)
        var letterGrade : String = "A"
        letterGrade = if(grade > course.minA) {
            "A"
        } else if(grade > course.minB){
            "B"
        } else if(grade > course.minC){
            "C"
        } else if(grade > course.minD){
            "D"
        } else {
            "F"
        }

        courseGrade.text = buildString {
            append("Current Grade: ")
            append(letterGrade)
            append(" (")
            append(rounded)
            append(")")
        }

    }

    private fun showAssignments(assignments: List<CourseWithManyAssignments>) {
        val listOfAllAssignments : List<Assignment> = assignments[0].assignmentList
        val breakDownOneList : MutableList<Assignment> = ArrayList()
        val breakDownTwoList : MutableList<Assignment> = ArrayList()
        val breakDownThreeList : MutableList<Assignment> = ArrayList()
        val breakDownFourList : MutableList<Assignment> = ArrayList()
        val breakDownFiveList : MutableList<Assignment> = ArrayList()
        val breakDownFinalList : MutableList<Assignment> = ArrayList()

        for (assignment in listOfAllAssignments) {
            if(assignment.breakdownName == course.breakdown1Name){
                breakDownOneList.add(assignment)
            }
            if(assignment.breakdownName == course.breakdown2Name){
                breakDownTwoList.add(assignment)
            }
            if(assignment.breakdownName == course.breakdown3Name){
                breakDownThreeList.add(assignment)
            }
            if(assignment.breakdownName == course.breakdown4Name){
                breakDownFourList.add(assignment)
            }
            if(assignment.breakdownName == course.breakdown5Name){
                breakDownFiveList.add(assignment)
            }
            if(assignment.breakdownName == "Final"){
                breakDownFinalList.add(assignment)
            }
        }

        if(breakDownOneList.size > 0) {
            adapter = AssignmentAdapter(breakDownOneList)
            assignmentRecyclerViewBreakdownOne.adapter = adapter
        }

        if(breakDownTwoList.size > 0) {
            adapter = AssignmentAdapter(breakDownTwoList)
            assignmentRecyclerViewBreakdownTwo.adapter = adapter
        }

        if(breakDownThreeList.size > 0) {
            adapter = AssignmentAdapter(breakDownThreeList)
            assignmentRecyclerViewBreakdownThree.adapter = adapter
        }

        if(breakDownFourList.size > 0) {
            adapter = AssignmentAdapter(breakDownFourList)
            assignmentRecyclerViewBreakdownFour.adapter = adapter
        }

        if(breakDownFiveList.size > 0) {
            adapter = AssignmentAdapter(breakDownFiveList)
            assignmentRecyclerViewBreakdownFive.adapter = adapter
        }

        if(breakDownFinalList.size > 0) {
            adapter = AssignmentAdapter(breakDownFinalList)
            assignmentRecyclerViewBreakdownFinal.adapter = adapter
        }

    }

    // Change the menu from adding course to assignment
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
           inflater.inflate(R.menu.fragment_assignment_list,menu)
        }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.new_assignment ->
            {
                Log.d(TAG,"Directing user to create assignment form")
                callbacks?.onAddAssignmentSelected(course.CourseID, semester.id)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {
        fun newInstance(courseId: UUID): CourseFragment {
            val args = Bundle().apply {
                putSerializable(ARG_COURSE_ID, courseId)
            }
            return CourseFragment().apply{
                arguments = args
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    private inner class AssignmentHolder(view: View)
        :RecyclerView.ViewHolder(view), View.OnClickListener{
        private lateinit var assignment: Assignment

        private val assignmentNameTextView: TextView = itemView.findViewById(R.id.assignment_name_title)
        private val assignmentGradeTextView: TextView = itemView.findViewById(R.id.assignment_list_grade)
        private val deleteAssignmentButton: Button = itemView.findViewById(R.id.delete_assignment_Button)
        private val editAssignmentButton: Button = itemView.findViewById(R.id.edit_assignment_Button)

        init {
            itemView.setOnClickListener(this)
            editAssignmentButton.setOnClickListener {
                callbacks?.onEditAssignmentPressed(assignment.AssignmentID, course.CourseID,semester.id)
            }
            deleteAssignmentButton.setOnClickListener {
                deleteAssignment(this.assignment)
            }
        }


        fun bind(assignment: Assignment) {
            this.assignment = assignment
            assignmentNameTextView.text = this.assignment.assignmentName
            // Must use buildString to set text with multiple arguments
            assignmentGradeTextView.text = buildString {
                    append("Grade: ")
                    append(assignment.currentPoints)
                    append("/")
                    append(assignment.maximumPoints)
                }
        }

        override fun onClick(v: View?) {
        }
    }

    private inner class AssignmentAdapter (var assignmentList: List<Assignment>)
        :RecyclerView.Adapter<AssignmentHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AssignmentHolder {
            val view = layoutInflater.inflate(R.layout.list_item_assignment,parent,false)
            return AssignmentHolder(view)
        }

        override fun onBindViewHolder(holder: AssignmentHolder, position: Int) {
            val assignment = assignmentList[position]
            holder.bind(assignment)
        }

        override fun getItemCount() = assignmentList.size
    }

    private fun updateUI(course: Course){
        this.course = course
        //Set course title
        courseTitle.text = "Class: ${course.courseName.uppercase()}"

        //Show sections if there is a breakdown, and set title for breakdown section
        if(course.breakdown1Name != "") {
            breakdownOneSection.visibility = View.VISIBLE
            breakdownOneTitle.text = course.breakdown1Name
        }
        if(course.breakdown2Name != "") {
            breakdownTwoSection.visibility = View.VISIBLE
            breakdownTwoTitle.text = course.breakdown2Name
        }
        if(course.breakdown3Name != "") {
            breakdownThreeSection.visibility = View.VISIBLE
            breakdownThreeTitle.text = course.breakdown3Name
        }
        if(course.breakdown4Name != "") {
            breakdownFourSection.visibility = View.VISIBLE
            breakdownFourTitle.text = course.breakdown4Name
        }
        if(course.breakdown5Name != "") {
            breakdownFiveSection.visibility = View.VISIBLE
            breakdownFiveTitle.text = course.breakdown5Name
        }
    }

    /* delete assignment */
    private fun deleteAssignment(assignment: Assignment){
        // Confirmation dialog
        var builder =  AlertDialog.Builder(activity)
        builder.setTitle("Delete Assignment")
        builder.setMessage("Are you sure you want to delete ${assignment.assignmentName.uppercase()}?")
        builder.setPositiveButton("Yes",DialogInterface.OnClickListener{dialog, id ->
            assignmentListViewModel.deleteAssignment(assignment)
            callbacks?.refreshAssignmentPage()

            Toast.makeText(context,"${assignment.assignmentName.uppercase()} deleted", Toast.LENGTH_SHORT)
                .show()
            dialog.cancel()

        })
        builder.setNegativeButton("No", DialogInterface.OnClickListener { dialog, id->
            dialog.cancel()
        })

        var alert: AlertDialog = builder.create()
        alert.show()
    }
}