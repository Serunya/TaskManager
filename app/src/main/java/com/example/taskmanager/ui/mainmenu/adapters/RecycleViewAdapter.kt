package com.example.taskmanager.ui.mainmenu.adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.taskmanager.R
import com.example.taskmanager.api.UserController
import com.example.taskmanager.payload.response.TaskResponse
import com.example.taskmanager.ui.task.TaskActivity

class RecycleViewAdapter(val context: Context,val tasks: ArrayList<TaskResponse> )
    : RecyclerView.Adapter<RecycleViewAdapter.ViewHolder>(){

        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
            val title = itemView.findViewById<TextView>(R.id.taskViewTitle)
            val description = itemView.findViewById<TextView>(R.id.taskViewDescription)
            val date = itemView.findViewById<TextView>(R.id.taskViewDate)
            val executor = itemView.findViewById<TextView>(R.id.taskViewExecutor)
            val view = itemView
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.task_view, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = tasks.get(position).title
        holder.description.text = tasks.get(position).description
        holder.executor.text = UserController.getUser(tasks.get(position).executorId)
        holder.date.text = tasks.get(position).deadLine
        holder.view.setOnClickListener({
            val intent = Intent(context, TaskActivity::class.java)
            intent.putExtra("taskId", tasks.get(position).id)
            context.startActivity(intent)
        })
    }

    override fun getItemCount(): Int {
        return tasks.size
    }
}