package com.example.taskmanager.ui.task.MainFragment

import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.taskmanager.R
import com.example.taskmanager.api.TaskApi
import com.example.taskmanager.payload.response.PointResponse
import com.example.taskmanager.payload.response.TaskResponse
import com.example.taskmanager.ui.mainmenu.MainMenu
import com.example.taskmanager.ui.task.TaskActivity
import com.example.taskmanager.ui.task.TaskVM
import kotlinx.coroutines.launch
import okhttp3.*

class MainTaskFragment() : Fragment() {

    val mainTaskVM : MainTaskVM by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val taskActivityVM = ViewModelProvider(requireActivity()).get(TaskVM::class.java)
        val task = taskActivityVM.task
        val userId = taskActivityVM.userId
        val convertView = inflater.inflate(R.layout.fragment_main_task, container, false)
        convertView.findViewById<TextView>(R.id.taskViewTitle).text = task?.title ?: "Название"
        convertView.findViewById<TextView>(R.id.taskViewDescription).text = task?.description ?: "Описание"
        convertView.findViewById<TextView>(R.id.taskViewDate).text = task?.deadLine ?: "1.1.1978"
        convertView.findViewById<TextView>(R.id.taskViewExecutor).text = taskActivityVM.executor
        val adapter = PointAdapter(requireContext(),taskActivityVM.points.sorted(),task?.taskStatus.equals("Done") || userId == task?.executorId,mainTaskVM)
        convertView.findViewById<ListView>(R.id.pointList).adapter = adapter
        if(userId == task?.executorId && !task.taskStatus.equals("Done")) {
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