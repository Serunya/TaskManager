package com.example.taskmanager.ui.authorize.Login

import android.app.AlertDialog
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NO_HISTORY
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.example.taskmanager.R
import com.example.taskmanager.api.ApiClient
import com.example.taskmanager.payload.response.BaseResponse
import com.example.taskmanager.ui.authorize.registry.SignUpFragment
import com.example.taskmanager.ui.mainmenu.MainMenu


class SignInFragmanet() : Fragment() {
    private lateinit var viewModel : LoginVM
    private lateinit var loadingDialog : AlertDialog
    val updateFragment = MutableLiveData<Boolean>(false)



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
        viewModel = ViewModelProvider(this).get(LoginVM::class.java)
        if(!ApiClient.access_token.equals("")){
            navigateToHome()
        }
        val fragment = inflater.inflate(R.layout.fragment_sign_in, container, false);
        fragment.findViewById<TextView>(R.id.swipeInToUp).setOnClickListener{
            updateFragment.value = true
        }
        val errorTV : TextView = fragment.findViewById(R.id.errorView)
        viewModel.loginResult.observe(viewLifecycleOwner){
            when(it){
                is BaseResponse.Loading -> {
                    loadingDialog.show()
                }
                is BaseResponse.Error -> {
                    loadingDialog.dismiss()
                    errorTV.text = it.msg;
                    if(it.code == 401)
                        errorTV.text = "Неверный логин или пароль"
                }
                is BaseResponse.Success ->{
                    ApiClient.access_token = it.data!!.access_token
                    ApiClient.refresh_token = it.data.refresh_token
                    viewModel.tokenRepository.saveAccessToken(it.data.access_token)
                    viewModel.tokenRepository.saveRefreshToken(it.data.refresh_token)
                    loadingDialog.dismiss()
                    navigateToHome()
                }
            }
        }
        val signInButton = fragment.findViewById<Button>(R.id.cirLoginButton)
        signInButton.setOnClickListener {
            val login = fragment.findViewById<EditText>(R.id.editTextLogin).text.toString()
            val pass = fragment.findViewById<EditText>(R.id.editTextPassword).text.toString()
            viewModel.loginUser(login,pass)
        }
        return fragment
    }


    private fun navigateToHome() {
        val intent = Intent(context, MainMenu::class.java)
        loadingDialog.dismiss()
        startActivity(intent)
    }


}