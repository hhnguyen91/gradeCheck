@file:Suppress("DEPRECATION")
package com.example.gradecheckhhn

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*

private const val TAG = "ClassListFragment"
class SemesterClassListFragment : Fragment(){

    interface Callbacks {
        fun onClassSelected(classId : UUID)
    }

    private var callbacks: Callbacks? = null

    private lateinit var classRecyclerView: RecyclerView

    private var adapter: ClassAdapter = ClassAdapter(emptyList())

    private val classListViewModel : SemesterClassListViewModel by lazy {
        ViewModelProviders.of(this).get(SemesterClassListViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG,"Whooho it works")
    }

    companion object {
        fun newInstance(): SemesterClassListFragment {
            return SemesterClassListFragment()
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

        classRecyclerView = view.findViewById((R.id.class_recycler_view)) as RecyclerView
        classRecyclerView.layoutManager = LinearLayoutManager(context)

        updateUI()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Incomplete
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    private fun updateUI(){
        val SemesterClass = classListViewModel.SemesterClasses
        adapter = ClassAdapter(SemesterClass)
        classRecyclerView.adapter = adapter
    }

    private inner class ClassHolder(view: View)
        :RecyclerView.ViewHolder(view), View.OnClickListener{

        private lateinit var semesterClass : SemesterClass

        private val classNameTextView: TextView = itemView.findViewById(R.id.class_title)
        private val classDepartmentTextView : TextView = itemView.findViewById(R.id.class_department_title)
        private val classSectionTextView : TextView = itemView.findViewById(R.id.class_section_title)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind (semesterClass: SemesterClass) {
            this.semesterClass = semesterClass
            classNameTextView.text = this.semesterClass.className
            classDepartmentTextView.text = this.semesterClass.department
            classSectionTextView.text = this.semesterClass.sectionNumber.toString()
        }

        override fun onClick(v: View?) {
            Toast.makeText(context, "${semesterClass.className} pressed!", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private inner class ClassAdapter(var semesterClass: List<SemesterClass>)
        :RecyclerView.Adapter<ClassHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClassHolder {
            val view = layoutInflater.inflate(R.layout.list_item_class,parent, false)
            return ClassHolder(view)
        }

        override fun onBindViewHolder(holder: ClassHolder, position: Int) {
            val semesterClass = semesterClass[position]
            holder.bind(semesterClass)
        }
        override fun getItemCount() = semesterClass.size

        }


}