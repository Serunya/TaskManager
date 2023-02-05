package com.example.taskmanager.ui.authorize.registry

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.replace
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import com.example.taskmanager.R
import com.example.taskmanager.payload.response.BaseResponse
import com.example.taskmanager.ui.authorize.Login.SignInFragmanet

class SignUpFragment : Fragment() {

    val updateFragment = MutableLiveData(false)
    val viewModel: SignUpVM by viewModels()
    private lateinit var loadingDialog : AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setCancelable(false)
        builder.setView(R.layout.loading_dialog)
        loadingDialog = builder.create()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val inflateView = inflater.inflate(R.layout.fragment_sign_up, container, false)
        val swipeUpToIn = inflateView.findViewById<TextView>(R.id.swipeUpToIn)
        swipeUpToIn.setOnClickListener {
            updateFragment.value = true
        }
        inflateView.findViewById<Button>(R.id.signUpButton).setOnClickListener {
            val firstName = inflateView.findViewById<EditText>(R.id.regFirstName).text.toString()
            val secondName= inflateView.findViewById<EditText>(R.id.regLastName).text.toString()
            val login = inflateView.findViewById<EditText>(R.id.regLogin).text.toString()
            val pass = inflateView.findViewById<EditText>(R.id.regPassword).text.toString()
            viewModel.regUser(login,pass, firstName, secondName)
        }

        viewModel.regResult.observe(viewLifecycleOwner){
            when(it){
                is BaseResponse.Loading -> {
                    loadingDialog.show()
                }
                is BaseResponse.Error -> {
                    loadingDialog.dismiss()
                    Toast.makeText(context,"Неизвестная ошибка",Toast.LENGTH_LONG).show()
                }
                is BaseResponse.Success -> {
                    updateFragment.value = true
                    loadingDialog.dismiss()
                }
            }
        }
        return inflateView
    }

}