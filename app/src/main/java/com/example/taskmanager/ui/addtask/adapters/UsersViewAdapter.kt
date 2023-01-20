package com.example.taskmanager.ui.addtask.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.setFragmentResult
import androidx.recyclerview.widget.RecyclerView
import com.example.taskmanager.R
import com.example.taskmanager.payload.response.UserResponse
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class UsersViewAdapter(val context: FragmentActivity, val users: ArrayList<UserResponse> ,val b : BottomSheetDialogFragment   ) :
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
            b.dismiss()
            val bundle = Bundle()
            bundle.putInt("userId",user.id)
            bundle.putString("Name",user.firstName + " " + user.secondName)
            b.setFragmentResult("user",bundle)
        })
    }

    override fun getItemCount(): Int {
        return users.size
    }
}