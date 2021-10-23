@file:Suppress("DEPRECATION")
package com.example.gradecheckhhn

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*

private const val TAG = "ClassListFragment"
class CourseListFragment : Fragment(){

    private var callbacks: Callbacks? = null

    private lateinit var courseRecyclerView: RecyclerView

    private var adapter: ClassAdapter = ClassAdapter(emptyList())

    private val courseListViewModel : CourseListViewModel by lazy {
        ViewModelProviders.of(this).get(CourseListViewModel::class.java)
    }

    interface Callbacks {
        fun onClassSelected(courseId : UUID)
        fun onAddCourseSelected()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG,"Wow it works")
        //setHasOptionsMenu(true)
    }


    companion object {
        fun newInstance(): CourseListFragment {
            return CourseListFragment()
        }
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
        val view = inflater.inflate(R.layout.fragment_semester,container, false)

        courseRecyclerView = view.findViewById((R.id.course_recycler_view)) as RecyclerView
        courseRecyclerView.layoutManager = LinearLayoutManager(context)
        courseRecyclerView.adapter = adapter

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Page 238, but for now this gives me a constant error
        /*
        courseListViewModel.courseListLiveData.observe(
            viewLifecycleOwner,
            Observer { courses ->
                courses?.let {
                    Log.i(TAG,"Got course $courses.size}")
                    updateUI(courses)
            }

            })*/

        // Incomplete
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    private fun updateUI(courses:List<Course>){
        adapter = ClassAdapter(courses)
        courseRecyclerView.adapter = adapter
    }

    private inner class ClassHolder(view: View)
        :RecyclerView.ViewHolder(view), View.OnClickListener{

        private lateinit var course : Course

        private val classNameTextView: TextView = itemView.findViewById(R.id.class_title)
        private val classDepartmentTextView : TextView = itemView.findViewById(R.id.class_department_title)
        private val classSectionTextView : TextView = itemView.findViewById(R.id.class_section_title)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind (course: Course) {
            this.course = course
            classNameTextView.text = this.course.courseName
            classDepartmentTextView.text = this.course.department
            classSectionTextView.text = this.course.sectionNumber.toString()
        }

        override fun onClick(v: View?) {
            callbacks?.onClassSelected(course.CourseID)
        }
    }

    private inner class ClassAdapter(var semesterClass: List<Course>)
        :RecyclerView.Adapter<ClassHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClassHolder {
            val view = layoutInflater.inflate(R.layout.list_item_course,parent, false)
            return ClassHolder(view)
        }

        override fun onBindViewHolder(holder: ClassHolder, position: Int) {
            val semesterClass = semesterClass[position]
            holder.bind(semesterClass)
        }
        override fun getItemCount() = semesterClass.size

        }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_course_list,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.new_course -> {
                val course = Course()
                courseListViewModel.addCourse(course)
                callbacks?.onClassSelected(course.CourseID)
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

}