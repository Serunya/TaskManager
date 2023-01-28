package com.example.taskmanager.ui.authorize

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.fragment.app.FragmentTransaction
import com.example.taskmanager.R
import com.example.taskmanager.ui.authorize.Login.SignInFragmanet
import com.example.taskmanager.ui.authorize.registry.SignUpFragment
import kotlin.math.sign

class AuthActivity : AppCompatActivity() {


    val signInFragmanet = SignInFragmanet()
    val signUpFragmanet = SignUpFragment()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        if (savedInstanceState == null) {
            val fragmentTranscation = supportFragmentManager.beginTransaction()
            fragmentTranscation.add(R.id.viewAuthorize, signInFragmanet)
            fragmentTranscation.commit()
        }

        signInFragmanet.updateFragment.observe(this) {
            if (it) {
                val fragmentTranscation = supportFragmentManager.beginTransaction()
                fragmentTranscation.remove(signInFragmanet)
                fragmentTranscation.add(R.id.viewAuthorize, signUpFragmanet)
                fragmentTranscation.commit()
                signInFragmanet.updateFragment.value = false
            }
        }
        signUpFragmanet.updateFragment.observe(this) {
            if (it) {
                val fragmentTranscation = supportFragmentManager.beginTransaction()
                fragmentTranscation.remove(signUpFragmanet)
                fragmentTranscation.add(R.id.viewAuthorize, signInFragmanet)
                fragmentTranscation.commit()
                signUpFragmanet.updateFragment.value = false
            }
        }
    }
}