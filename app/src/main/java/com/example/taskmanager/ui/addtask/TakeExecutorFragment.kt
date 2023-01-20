package com.example.taskmanager.ui.addtask

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taskmanager.R

class TakeExecutorFragment(val addFragment: addFragment) : Fragment() {
    private val viewModel : TakeExecutorVM by viewModels()
    private lateinit var userAdapter : UsersViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userAdapter = UsersViewAdapter(requireActivity(),viewModel.users,addFragment)
    }


    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_todo, container, false)
        val list = view.findViewById<RecyclerView>(R.id.listView)
        list.adapter = userAdapter
        viewModel.getUsers()
        viewModel.usersResult.observe(viewLifecycleOwner){
            userAdapter.notifyDataSetChanged()
        }
        list.layoutManager = LinearLayoutManager(requireContext())
        return view
    }
}