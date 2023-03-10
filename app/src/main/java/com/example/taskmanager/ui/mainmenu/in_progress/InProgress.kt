package com.example.taskmanager.ui.mainmenu.in_progress

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taskmanager.R
import com.example.taskmanager.api.TaskController
import com.example.taskmanager.ui.mainmenu.adapters.RecycleViewAdapter

class InProgress : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val layout = inflater.inflate(R.layout.fragment_todo, container, false)
        val listView : RecyclerView = layout.findViewById(R.id.listView)
        val adapter = RecycleViewAdapter(requireActivity(), TaskController.inProgressTasks.value!!)
        listView.adapter = adapter
        listView.layoutManager = LinearLayoutManager(requireContext())
        TaskController.inProgressTasks.observe(viewLifecycleOwner){
            adapter.notifyDataSetChanged()
        }
        return layout
    }
}