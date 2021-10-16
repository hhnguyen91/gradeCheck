@file:Suppress("DEPRECATION")
package com.example.gradecheckhhn

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class SemesterClassListFragment : Fragment(){

    interface Callbacks {
        fun onClassSelected(classId : UUID)
    }

    private var callbacks: Callbacks? = null

    private lateinit var classRecyclerView: RecyclerView
    private var adapter: ClassAcapter = ClassAdapter(emptyList())

    private val classListViewModel : ClassListViewModel by lazy {
        ViewModelProviders.of(this).get(ClassListViewModel::class.java)
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
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    private inner class ClassHolder(view: View)
        :RecyclerView.ViewHolder(view), View.OnClickListener{

            fun bind (semesterClass: SemesterClass) {

            }

        override fun onClick(v: View?) {
            callbacks?.onClassSelected(SemesterClass.id)
        }
    }

}