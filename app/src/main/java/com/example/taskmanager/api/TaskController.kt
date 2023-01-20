package com.example.taskmanager.api

import androidx.lifecycle.MutableLiveData
import com.example.taskmanager.payload.response.TaskResponse

object TaskController {
    val tasks : HashMap<Int,TaskResponse> = HashMap<Int,TaskResponse>()
    val todoTasks : MutableLiveData<ArrayList<TaskResponse>> = MutableLiveData<ArrayList<TaskResponse>>(ArrayList())
    val inProgressTasks : MutableLiveData<ArrayList<TaskResponse>> = MutableLiveData<ArrayList<TaskResponse>>(ArrayList())
    val doneTasks : MutableLiveData<ArrayList<TaskResponse>> = MutableLiveData(ArrayList())



    fun addTasks(tasks: Array<TaskResponse>?){
        todoTasks.value?.clear()
        inProgressTasks.value?.clear()
        doneTasks.value?.clear()
        for(task in tasks!!){
            this.tasks.put(task.id,task)
            when(task.taskStatus){
                "TO DO" -> { todoTasks.value?.add(task)}
                "In Progress" -> { inProgressTasks.value?.add(task)}
                "Done" -> { doneTasks.value?.add(task) }
            }
        }
    }


}