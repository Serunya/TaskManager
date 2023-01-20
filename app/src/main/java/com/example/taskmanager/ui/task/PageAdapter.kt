package com.example.rttodolist.ui.todotask

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.rttodolist.ui.chat.ChatFragment
import com.example.taskmanager.payload.response.PointResponse
import com.example.taskmanager.payload.response.TaskResponse
import com.example.taskmanager.ui.task.MainFragment.MainTaskFragment


class PageAdapter(fm: FragmentActivity,val task : TaskResponse, val points : ArrayList<PointResponse>,val userId: Int,val executor: String) : FragmentStateAdapter(fm) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> MainTaskFragment(task, points,userId,executor)
            1 -> ChatFragment()
            else -> MainTaskFragment(task,points,userId,executor)
        }
    }

}

