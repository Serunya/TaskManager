package com.example.rttodolist.ui.todotask

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.rttodolist.ui.chat.ChatFragment
import com.example.taskmanager.payload.response.PointResponse
import com.example.taskmanager.payload.response.TaskResponse
import com.example.taskmanager.ui.task.MainFragment.MainTaskFragment


class PageAdapter(fm: FragmentActivity) : FragmentStateAdapter(fm) {

    val mainTaskFragment = MainTaskFragment()
    val chatFragment = ChatFragment()


    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> mainTaskFragment
            1 -> chatFragment
            else -> mainTaskFragment
        }
    }

}

