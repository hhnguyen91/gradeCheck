@file:Suppress("DEPRECATION")
package com.example.gradecheckhhn

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*

private const val TAG = "SemesterFragment"
private const val ARG_SEMESTER_ID = "semester_id"

class SemesterFragment : Fragment() {

    private lateinit var semester: Semester
    private lateinit var semesterTitle: TextView

    private var callbacks: Callbacks? = null
    private lateinit var courseRecyclerView: RecyclerView

    private var adapter: CourseAdapter = CourseAdapter(emptyList())

    private val semesterDetailViewModel: SemesterDetailViewModel by lazy {
        ViewModelProviders.of(this).get(SemesterDetailViewModel::class.java)
    }

    private val courseListViewModel : CourseListViewModel by lazy {
        ViewModelProviders.of(this).get(CourseListViewModel::class.java)
    }


    interface Callbacks {
        fun onCourseSelected(courseId: UUID)
        //Should Direct user to create course Screen
        fun onAddCourseSelected()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        semester = Semester()
        val semesterId: UUID = arguments?.getSerializable(ARG_SEMESTER_ID) as UUID
        Log.i(TAG,"Semester $semesterId selected")
        semesterDetailViewModel.loadSemester(semesterId)
        // Must be true for the app bar change to happen
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_semester, container, false)
        semesterTitle = view.findViewById(R.id.semester_title) as TextView

        courseRecyclerView = view.findViewById(R.id.course_recycler_view) as RecyclerView
        courseRecyclerView.layoutManager = LinearLayoutManager(context)
        courseRecyclerView.adapter = adapter

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
                    UpperCaseText()
                }

            }
        )

        courseListViewModel.courseListLiveData.observe(
            viewLifecycleOwner,
            Observer{ courses->
                courses?.let {
                updateUI(courses)
            }
            }

        )
    }

    // Change the menu from adding semester to course
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.fragment_course_list,menu)
    }

    private fun UpperCaseText() {
        semesterTitle.text = "${semester.season.uppercase()} ${semester.year}"
    }

    private fun updateUI(courses: List<Course>) {
        Log.i(TAG,"Got ${courses.size} courses")
        adapter = CourseAdapter(courses)
        //courseRecyclerView.adapter = adapter
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.new_course ->
            {
                //val course = Course()
                //courseListViewModel.addCourse(course)
                //callbacks?.onCourseSelected(course.CourseID)
                Log.d(TAG,"Directing user to create course form")
                callbacks?.onAddCourseSelected()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    // Handles the course item functionality
    private inner class CourseHolder (view: View)
        :RecyclerView.ViewHolder(view), View.OnClickListener{
        private lateinit var course: Course

        private val courseNameTextView: TextView = itemView.findViewById(R.id.class_title)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind (course: Course) {
            this.course = course
            courseNameTextView.text = this.course.courseName
        }

        override fun onClick(v: View?) {
            callbacks?.onCourseSelected(course.CourseID)
        }

    }

    private inner class CourseAdapter (var courseList: List<Course>)
        :RecyclerView.Adapter<CourseHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseHolder {
            val view = layoutInflater.inflate(R.layout.list_item_course,parent)
            return CourseHolder(view)
        }

        override fun onBindViewHolder(holder: CourseHolder, position: Int) {
            val course = courseList[position]
            holder.bind(course)
        }

        override fun getItemCount() = courseList.size
    }

    /* delete course */
    private fun deleteCourse(course: Course) {

         //Creating a confirmation dialog
         var builder = AlertDialog.Builder(activity)
         builder.setTitle("Delete Course")
         builder.setMessage("Are you sure you want to delete ${course.courseName.uppercase()}?")
         builder.setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, id->
             courseListViewModel.deleteCourse(course)

             Toast.makeText(context, "${course.courseName.uppercase()} deleted", Toast.LENGTH_SHORT)
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