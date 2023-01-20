package com.example.taskmanager.ui.task.MainFragment

import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.example.taskmanager.R
import com.example.taskmanager.api.TaskApi
import com.example.taskmanager.payload.response.PointResponse
import com.example.taskmanager.payload.response.TaskResponse
import com.example.taskmanager.ui.mainmenu.MainMenu
import kotlinx.coroutines.launch
import okhttp3.*

class MainTaskFragment(val task: TaskResponse, val point: ArrayList<PointResponse>, val userId : Int,val executor: String) : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val convertView = inflater.inflate(R.layout.fragment_main_task, container, false)
        convertView.findViewById<TextView>(R.id.taskViewTitle).text = task.title
        convertView.findViewById<TextView>(R.id.taskViewDescription).text = task.description
        convertView.findViewById<TextView>(R.id.taskViewDate).text = task.deadLine
        convertView.findViewById<TextView>(R.id.taskViewExecutor).text = executor
        val adapter = PointAdapter(requireContext(),point,userId == task.executorId)
        convertView.findViewById<ListView>(R.id.pointList).adapter = adapter
        if(userId == task.executorId && !task.taskStatus.equals("Done")) {
            val button = convertView.findViewById<Button>(R.id.successButton)
            button.visibility = View.VISIBLE
            button.setOnClickListener {
                lifecycleScope.launch() {
                    TaskApi.getApi()?.upgradeStatus(task.id)
                    startActivity(Intent(context,MainMenu::class.java))
                }
            }

        }
        return convertView
    }
}