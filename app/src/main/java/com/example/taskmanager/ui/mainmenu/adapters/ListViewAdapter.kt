package com.example.taskmanager.ui.mainmenu.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.taskmanager.R
import com.example.taskmanager.api.TaskController
import com.example.taskmanager.payload.response.TaskResponse
import com.example.taskmanager.ui.task.TaskActivity

class ListViewAdapter(context: Context, list: ArrayList<TaskResponse>) : ArrayAdapter<TaskResponse>(context, R.layout.task_view, list ) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        if(convertView == null){
             return getView(position, LayoutInflater.from(context).inflate(R.layout.task_view,null),parent)
        }
        val task : TaskResponse? = getItem(position)
        if(task != null) {
            convertView.setOnClickListener(View.OnClickListener {
                val intent = Intent(context, TaskActivity::class.java)
                intent.putExtra("taskId", task.id)
                context.startActivity(intent)
            })
            convertView.findViewById<TextView>(R.id.taskViewTitle)?.setText(task.title)
            convertView.findViewById<TextView>(R.id.taskViewDescription)?.setText(task.description)
            convertView.findViewById<TextView>(R.id.taskViewDate)?.setText(task.deadLine)
        }
        return convertView
    }
}