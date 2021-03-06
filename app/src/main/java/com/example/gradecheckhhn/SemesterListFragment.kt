@file:Suppress("DEPRECATION")

package com.example.gradecheckhhn

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gradecheckhhn.databaseEntities.Semester
import java.util.*

private const val TAG = "SemesterListFragment"

class SemesterListFragment : Fragment() {



    private lateinit var semesterRecyclerView: RecyclerView
    private var adapter: SemesterAdapter? = SemesterAdapter(emptyList())
    private val semesterListViewModel : SemesterListViewModel by lazy {
        ViewModelProviders.of(this).get(SemesterListViewModel::class.java)
    }

    private var callbacks: Callbacks? = null

    /**
     * Required interface for hosting activities
     */

    interface Callbacks {
        fun onSemesterSelected(semesterId: UUID)
        fun onEditSemesterPressed(semesterId: UUID)
        fun onAddSemesterSelected()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_semester_list, container, false)

        semesterRecyclerView =
            view.findViewById(R.id.semester_recycler_view) as RecyclerView
        semesterRecyclerView.layoutManager = LinearLayoutManager(context)
        semesterRecyclerView.adapter = adapter
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onStart() {
        super.onStart()
        semesterListViewModel.semesterListLiveData.observe(
            viewLifecycleOwner,
            Observer { semesters ->
                semesters?.let {
                    updateUI(semesters)
                }
            }
        )

    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_semester_list, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.new_semester -> {
                Log.d(TAG,"Directing user to create semester form")
                callbacks?.onAddSemesterSelected()
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun updateUI(semesters: List<Semester>) {
        adapter = SemesterAdapter(semesters)
        semesterRecyclerView.adapter = adapter
        Log.i(TAG, "Got semesters ${semesters.size}")
        //Log.i(TAG, "Semester ${semesters[semesters.size -1].year} ${semesters[semesters.size -1].season}")
    }

    // Handles the semester item functionality
    private inner class SemesterHolder(view: View)
        : RecyclerView.ViewHolder(view), View.OnClickListener {

        private lateinit var semester: Semester

        //TextViews for the semester view on the SemesterList fragment
        private val seasonTextView: TextView = itemView.findViewById(R.id.semester_season)
        private val yearTextView: TextView = itemView.findViewById(R.id.semester_year)
        private val editButton: Button = itemView.findViewById(R.id.edit_Button)
        private val deleteButton: Button = itemView.findViewById(R.id.delete_Button)



        init {
            itemView.setOnClickListener(this)
            //editButton.setOnClickListener()
            editButton.setOnClickListener{
                callbacks?.onEditSemesterPressed(semester.id)
            }
            deleteButton.setOnClickListener{
                deleteSemester(this.semester)
            }
        }

        fun bind(semester: Semester) {
            this.semester = semester
            seasonTextView.text = this.semester.season.uppercase()
            yearTextView.text = this.semester.year
            Log.i(TAG,"Binded ${this.semester.season} ${this.semester.year}")
        }

        override fun onClick(v: View){
            Log.d(TAG, "MainActivity.onCrimeSelected: ${this.semester.id}")
            callbacks?.onSemesterSelected(semester.id)
        }

    }

    // Bridge the database to the UI component
    private inner class SemesterAdapter(var semesters: List<Semester>)
        : RecyclerView.Adapter<SemesterHolder>(){

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
                : SemesterHolder {
            val layoutInflater = LayoutInflater.from(context)
            val view = layoutInflater.inflate(R.layout.list_item_semester, parent, false)
            return SemesterHolder(view)
        }

        override fun onBindViewHolder(holder: SemesterHolder, position: Int) {
            val semester = semesters[position]
            holder.bind(semester)
        }

        override fun getItemCount() = semesters.size
    }

    private fun deleteSemester(semester: Semester) {

        //Creating a confirmation dialog
        var builder = AlertDialog.Builder(activity)
        builder.setTitle("Delete Semester")
        builder.setMessage("Are you sure you want to delete ${semester.season.uppercase()} ${semester.year}?")
        builder.setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, id->
            semesterListViewModel.deleteSemester(semester)

            Toast.makeText(context, "${semester.season.uppercase()} ${semester.year} deleted", Toast.LENGTH_SHORT)
                .show()
            dialog.cancel()
        })
        builder.setNegativeButton("No", DialogInterface.OnClickListener { dialog, id->
            dialog.cancel()
        })
        var alert: AlertDialog = builder.create()
        alert.show()

    }


    companion object {
        fun newInstance(): SemesterListFragment {
            return SemesterListFragment()
        }
    }


}
