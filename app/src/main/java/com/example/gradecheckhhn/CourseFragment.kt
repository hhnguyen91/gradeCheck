package com.example.gradecheckhhn

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
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

    private lateinit var breakDownOneVal: String

    private lateinit var assignmentRecyclerViewBreakdownOne: RecyclerView
    private lateinit var assignmentRecyclerViewBreakdownTwo: RecyclerView
    private lateinit var assignmentRecyclerViewBreakdownThree: RecyclerView
    private lateinit var assignmentRecyclerViewBreakdownFour: RecyclerView
    private lateinit var assignmentRecyclerViewBreakdownFive: RecyclerView

    private var adapter: AssignmentAdapter = AssignmentAdapter(emptyList())

    private val editCourseViewModel: CourseEditViewModel by lazy {
        ViewModelProviders.of(this).get(CourseEditViewModel::class.java)
    }

    private lateinit var assignmentListViewModel: AssignmentListViewModel
    private lateinit var assignmentListViewModelFactory: AssignmentListViewModelFactory

    private lateinit var courseTitle: TextView

    private var callbacks: Callbacks? = null

    interface Callbacks {
        fun onEditAssignmentPressed(assignmentID: UUID, courseId: UUID, semesterId: UUID)
        fun onAddAssignmentSelected(courseId: UUID, semesterId: UUID)
        fun onAssignmentSelected(assignmentID: UUID)
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

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val courseId = arguments?.getSerializable(ARG_COURSE_ID) as UUID
        Log.i(TAG,"Course: $courseId Selected")
        editCourseViewModel.loadCourse(courseId)
        editCourseViewModel.courseLiveData.observe(
            viewLifecycleOwner,
            Observer { course ->
                course?.let {
                    this.course = course
                    updateUI(course)
                }
            }
        )
       // courseTitle.text = "${course.courseName.uppercase()}"
       // Log.i(TAG,"Course: ${course.courseName} Selected")
    }


    override fun onStart() {
        super.onStart()
        Log.i(TAG,"Breakdown1: ${breakdownOneTitle.text}")

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
                }
            }
        )

    }

    private fun showAssignments(assignments: List<CourseWithManyAssignments>) {
        val listOfAllAssignments : List<Assignment> = assignments[0].assignmentList
        val breakDownOneList : MutableList<Assignment> = ArrayList()
        val breakDownTwoList : MutableList<Assignment> = ArrayList()
        val breakDownThreeList : MutableList<Assignment> = ArrayList()
        val breakDownFourList : MutableList<Assignment> = ArrayList()
        val breakDownFiveList : MutableList<Assignment> = ArrayList()

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
        private val assignmentCurrentScoreTextView: TextView = itemView.findViewById(R.id.assignment_list_current_score)
        private val assignmentMaxScoreTextView: TextView = itemView.findViewById(R.id.assignment_list_max_score)
        private val deleteAssignmentButton: Button = itemView.findViewById(R.id.delete_assignment_Button)
        private val editAssignmentButton: Button = itemView.findViewById(R.id.edit_assignment_Button)

        init {
            itemView.setOnClickListener(this)
            editAssignmentButton.setOnClickListener {
                callbacks?.onEditAssignmentPressed(assignment.AssignmentID, course.CourseID,semester.id)
            }
        }

        @SuppressLint("SetTextI18n")
        fun bind(assignment: Assignment) {
            this.assignment = assignment
            //val grade : String = "Grade ${this.assignment.currentPoints}/${this.assignment.maximumPoints}"
            Log.i(TAG,"Assignment Title: ${assignment.assignmentName}")
            Log.i(TAG,"Assignment current score: ${assignment.currentPoints}")
            Log.i(TAG,"Assignment max score: ${assignment.maximumPoints}")
            assignmentNameTextView.text = this.assignment.assignmentName
            assignmentCurrentScoreTextView.text = this.assignment.currentPoints.toString()
            assignmentMaxScoreTextView.text = this.assignment.maximumPoints.toString()
            //assignmentGradeTextView.text = "Grade ${this.assignment.currentPoints}/${this.assignment.maximumPoints}"
        }

        override fun onClick(v: View?) {
            callbacks?.onAssignmentSelected(assignment.AssignmentID)
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

}