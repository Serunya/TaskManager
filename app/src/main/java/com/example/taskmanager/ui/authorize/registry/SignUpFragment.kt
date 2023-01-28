package com.example.taskmanager.ui.authorize.registry

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.replace
import androidx.lifecycle.MutableLiveData
import com.example.taskmanager.R
import com.example.taskmanager.ui.authorize.Login.SignInFragmanet

class SignUpFragment : Fragment() {

    val updateFragment = MutableLiveData(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val inflateView = inflater.inflate(R.layout.fragment_sign_up, container, false)
        val swipeUpToIn = inflateView.findViewById<TextView>(R.id.swipeUpToIn)
        swipeUpToIn.setOnClickListener{
            updateFragment.value = true
        }
        inflateView.findViewById<Button>(R.id.signUpButton).setOnClickListener{
            val login = fragment.findViewById<EditText>(R.id.editTextLogin).text.toString()
            val pass = fragment.findViewById<EditText>(R.id.editTextPassword).text.toString()
            viewModel.loginUser(login,pass)
        }
        return inflateView
    }

}