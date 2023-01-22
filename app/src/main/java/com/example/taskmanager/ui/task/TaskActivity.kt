package com.example.taskmanager.ui.task

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.example.rttodolist.ui.todotask.PageAdapter
import com.example.taskmanager.R
import com.example.taskmanager.api.TaskApi
import com.example.taskmanager.payload.response.BaseResponse
import com.example.taskmanager.payload.response.PointResponse
import com.example.taskmanager.payload.response.TaskResponse
import com.example.taskmanager.ui.mainmenu.MainMenu
import com.example.taskmanager.ui.task.MainFragment.MainTaskVM
import com.example.taskmanager.ui.task.MainFragment.PointAdapter
import kotlinx.coroutines.launch

class TaskActivity : AppCompatActivity() {
    private val viewModel: TaskVM by viewModels()
    private lateinit var loadingDialog: AlertDialog

    private var endPointDownload: Boolean = false;
    private var endTaskDownload: Boolean = false;
    private var endUserDownload: Boolean = false;
    private var endExecutorDownload: Boolean = false;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task)
        if(intent.extras == null) {
            startActivity(
                Intent(
                    this,
                    MainMenu::class.java
                )
            )
        }
        val taskId: Int = intent.extras?.getInt("taskId") ?: 0
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setCancelable(false)
        builder.setView(R.layout.loading_dialog)
        loadingDialog = builder.create()
        loadingDialog.show()
        getUserId()
        getTask(taskId)
        getPoints(taskId)
    }


    private fun getUserId() {
        viewModel.getUser()
        viewModel.userResult.observe(this) {
            when (it) {
                is BaseResponse.Loading -> {
                }
                is BaseResponse.Success -> {
                    viewModel.userId = it.data?.id ?: 0
                    endUserDownload = true
                    endDownlaod()
                }
                is BaseResponse.Error -> {
                    loadingDialog.dismiss()
                    startActivity(Intent(this, MainMenu::class.java))
                }
            }
        }
    }

    private fun getPoints(taskId: Int) {
        viewModel.getAllPoints(taskId)
        viewModel.pointResult.observe(this) {
            when (it) {
                is BaseResponse.Loading -> {
                    loadingDialog.show()
                }
                is BaseResponse.Success -> {
                    viewModel.points.addAll(it.data!!.toList())
                    endPointDownload = true;
                    endDownlaod()
                }
                is BaseResponse.Error -> {
                    loadingDialog.dismiss()
                    startActivity(Intent(this, MainMenu::class.java))
                }
            }
        }
    }

    private fun getTask(taskId: Int) {
        viewModel.getTask(taskId)
        viewModel.taskResult.observe(this) {
            when (it) {
                is BaseResponse.Loading -> {
                }
                is BaseResponse.Success -> {
                    viewModel.task = it.data
                    getUserNameExecutor(it.data?.executorId ?: 1)
                    endTaskDownload = true
                    endDownlaod()
                }
                is BaseResponse.Error -> {
                    loadingDialog.dismiss()
                    startActivity(Intent(this, MainMenu::class.java))
                }
            }
        }
    }

    private fun getUserNameExecutor(executorId: Int) {
        viewModel.getExecutor(executorId)
        viewModel.executorResult.observe(this) {
            when (it) {
                is BaseResponse.Loading -> {
                }
                is BaseResponse.Success -> {
                    viewModel.executor = it.data!!.firstName + " " + it.data.secondName
                    endExecutorDownload = true
                    endDownlaod()
                }
                is BaseResponse.Error -> {
                    loadingDialog.dismiss()
                    startActivity(Intent(this, MainMenu::class.java))
                }
            }
        }
    }


    private fun endDownlaod() {
        if (endPointDownload && endTaskDownload && endUserDownload && endExecutorDownload) {
            loadingDialog.dismiss()
            val adapter = PageAdapter(this)
            val viewPager = findViewById<ViewPager2>(R.id.taskviewPager)
            viewPager.adapter = adapter
        }
    }

}