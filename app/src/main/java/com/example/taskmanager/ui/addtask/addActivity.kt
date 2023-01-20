package com.example.taskmanager.ui.addtask

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.taskmanager.R

class addActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        if(savedInstanceState == null) {
            supportFragmentManager.beginTransaction().add(R.id.addFragmentContriner, addFragment())
                .commit()
        }
    }
}