package com.example.taskmanager.ui.authorize

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.fragment.app.FragmentTransaction
import com.example.taskmanager.R
import com.example.taskmanager.ui.authorize.Login.SignInFragmanet

class AuthActivity : AppCompatActivity() {


    val signInFragmanet = SignInFragmanet()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        if(savedInstanceState == null) {
            val fragmentTranscation = supportFragmentManager.beginTransaction()
            fragmentTranscation.add(R.id.viewAuthorize, signInFragmanet)
            fragmentTranscation.commit()
        }
    }
}