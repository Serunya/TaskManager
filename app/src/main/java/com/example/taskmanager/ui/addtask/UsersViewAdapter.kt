package com.example.taskmanager.ui.addtask

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.replace
import androidx.fragment.app.setFragmentResult
import androidx.recyclerview.widget.RecyclerView
import com.example.taskmanager.R
import com.example.taskmanager.payload.response.UserResponse

class UsersViewAdapter(val context: FragmentActivity, val users: ArrayList<UserResponse>,val addFragment: addFragment) :
    RecyclerView.Adapter<UsersViewAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val firstName = itemView.findViewById<TextView>(R.id.firstName)
        val secondName = itemView.findViewById<TextView>(R.id.secondName)
        val view : View = itemView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.user_name_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = users.get(position)
        holder.firstName.text = user.firstName
        holder.secondName.text = user.secondName
        holder.view.setOnClickListener({
            val bundle = Bundle()
            bundle.putInt("userId",user.id)
            bundle.putString("firstName",user.firstName)
            bundle.putString("secondName",user.secondName)
            context.supportFragmentManager.setFragmentResult("user",bundle)
            context.supportFragmentManager.beginTransaction().replace(R.id.addFragmentContriner,addFragment).commit()
        })
    }

    override fun getItemCount(): Int {
        return users.size
    }
}