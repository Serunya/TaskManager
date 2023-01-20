package com.example.taskmanager.ui.addtask.fragments.bottomTakeExecutor

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taskmanager.R
import com.example.taskmanager.ui.addtask.adapters.UsersViewAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class TakeExecutorFragment : BottomSheetDialogFragment() {
    private val viewModel : TakeExecutorVM by viewModels()
    private lateinit var userAdapter : UsersViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userAdapter = UsersViewAdapter(requireActivity(),viewModel.users,this)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.bottom_sheet_executor, container, false)
        val list = view.findViewById<RecyclerView>(R.id.executorsList)
        list.adapter = userAdapter
        list.layoutManager = LinearLayoutManager(requireContext())
        viewModel.getUsers()
        viewModel.usersResult.observe(viewLifecycleOwner){
            userAdapter.notifyDataSetChanged()
        }
        return view
    }
}