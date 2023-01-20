package com.example.taskmanager.ui.mainmenu

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.viewModels
import androidx.navigation.NavHostController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.taskmanager.R
import com.example.taskmanager.api.ApiClient
import com.example.taskmanager.api.TaskApi
import com.example.taskmanager.api.TaskController
import com.example.taskmanager.databinding.ActivityMainMenuBinding
import com.example.taskmanager.payload.response.BaseResponse
import com.example.taskmanager.ui.addtask.addActivity
import com.example.taskmanager.ui.authorize.AuthActivity
import com.example.taskmanager.ui.authorize.Login.LoginVM
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar

class MainMenu : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainMenuBinding
    private val viewModel: MainMenuVM by viewModels()
    private lateinit var loadingDialog: AlertDialog


    private var endUserNameDownload = false;
    private var endTasksDownload = false;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.appBarMain.toolbar)
        binding.appBarMain.fab.setOnClickListener { view ->
            startActivity(Intent(this, addActivity::class.java))
        }
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setCancelable(false)
        builder.setView(R.layout.loading_dialog)
        loadingDialog = builder.create()
        loadingDialog.show()
        viewModel.requestUserName()
        viewModel.usernameResult.observe(this) {
            when (it) {
                is BaseResponse.Loading -> {
                }
                is BaseResponse.Success -> {
                    endUserNameDownload = true
                    binding.navView.findViewById<TextView>(R.id.header_firstname).text =
                        it.data?.firstName
                            ?: "Ошибка получения имени"
                    binding.navView.findViewById<TextView>(R.id.header_secondname).text =
                        it.data?.secondName
                            ?: "Ошибка получения фамилии"
                    endDowndload()
                }
                is BaseResponse.Error -> {
                    val intent = Intent(this, AuthActivity::class.java)
                    ApiClient.tokenRepository?.clearData()
                    Toast.makeText(this, "Ошибка авторизации", Toast.LENGTH_LONG).show()
                    startActivity(intent)
                }
            }
        }
        viewModel.getTask()
        viewModel.taskResult.observe(this) {
            when (it) {
                is BaseResponse.Loading -> {
                }
                is BaseResponse.Error -> {
                }
                is BaseResponse.Success -> {
                    TaskController.addTasks(it.data)
                    endTasksDownload = true
                    endDowndload()
                }
            }
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }


    private fun endDowndload() {
        if (endUserNameDownload && endTasksDownload) {
            loadingDialog.dismiss()
            val drawerLayout: DrawerLayout = binding.drawerLayout
            val navView: NavigationView = binding.navView
            val navController = findNavController(R.id.nav_host_fragment_content_main)
            appBarConfiguration = AppBarConfiguration(
                setOf(
                    R.id.nav_todo, R.id.nav_in_progress, R.id.nav_done
                ), drawerLayout
            )
            setupActionBarWithNavController(navController, appBarConfiguration)
            navView.setupWithNavController(navController)
        }
    }
}