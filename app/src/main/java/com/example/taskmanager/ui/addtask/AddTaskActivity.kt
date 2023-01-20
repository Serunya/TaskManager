package com.example.taskmanager.ui.addtask

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.setFragmentResultListener
import com.example.taskmanager.R
import com.example.taskmanager.payload.request.TaskRequest
import com.example.taskmanager.payload.response.BaseResponse
import com.example.taskmanager.ui.addtask.adapters.PointviewAdapter
import com.example.taskmanager.ui.addtask.fragments.bottomTakeExecutor.TakeExecutorFragment
import com.example.taskmanager.ui.mainmenu.MainMenu
import java.util.*
import kotlin.collections.ArrayList

class AddTaskActivity : AppCompatActivity() {

    var executorId: Int = 0
    var pointList = ArrayList<String>()
    private lateinit var adapter : PointviewAdapter
    private val addTaskVM : AddTaskVM by viewModels()
    private lateinit var loadingDialog : AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        //build LoadingDialog
        buildLoadingDialog()

        //takeExecutorBottomDialog
        findViewById<TextView>(R.id.addExecutor)
            .setOnClickListener { view -> startBottomDialog(view) }

        //Calendar
        createCalendarDialog()

        //Points
        createPointViewList()

        //SaveTaskButton
        findViewById<Button>(R.id.saveButtonFromAdd).setOnClickListener{view -> saveTask(view)}
    }

    private fun createPointViewList(){
        val pointListView: ListView = findViewById(R.id.pointList)
        adapter = PointviewAdapter(
            this,
            pointList
        )
        pointListView.setDivider(null);
        val addButton = LayoutInflater.from(this).inflate(R.layout.add_point,null)
        addButton.setOnClickListener { view : View ->
            pointList.add("")
            adapter.notifyDataSetChanged()
        }
        pointListView.addFooterView(addButton)
        pointListView.adapter = adapter
    }


    private fun buildLoadingDialog(){
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setCancelable(false)
        builder.setView(R.layout.loading_dialog)
        loadingDialog = builder.create()
    }


    private fun saveTask(view : View){
        val title = findViewById<EditText>(R.id.addTitle).text.toString()
        val description = findViewById<EditText>(R.id.addDescription).text.toString()
        val date = findViewById<TextView>(R.id.datePicker).text.toString().replace('.','/')
        if(title.equals("") || description.equals("") || date.equals("Выберите Дату") || executorId == 0){
            Toast.makeText(this,"Заполните все поля",Toast.LENGTH_LONG).show()
        }
        else {
            addTaskVM.saveTask(TaskRequest(title,description,executorId,date),pointList)
            addTaskVM.saveResult.observe(this){
                when(it){
                    is BaseResponse.Loading -> {
                        loadingDialog.show()
                    }
                    is BaseResponse.Success -> {
                        loadingDialog.dismiss()
                        startActivity(Intent(this,MainMenu::class.java))
                    }
                    is BaseResponse.Error -> {
                        loadingDialog.dismiss()
                        Toast.makeText(this,"Ошибка загрузки",Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }



    private fun createCalendarDialog(){
        val c = Calendar.getInstance()
        val d = c.get(Calendar.DAY_OF_MONTH)
        val m = c.get(Calendar.MONTH) + 1
        val y = c.get(Calendar.YEAR)
        val date = findViewById<TextView>(R.id.datePicker)
        date.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val dpd = DatePickerDialog(this@AddTaskActivity, { view, year, month, dayOfMonth ->
                    findViewById<TextView>(R.id.datePicker).text =
                        "$dayOfMonth.$month.$year"
                }, y, m, d)
                dpd.show()
            }
        })
    }

    private fun startBottomDialog(view: View) {
        val bottomSheetFragment = TakeExecutorFragment()
        bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.getTag())
        bottomSheetFragment.setFragmentResultListener("user") { key: String, bundle: Bundle ->
            executorId = bundle.getInt("userId",0)
            val executorName = bundle.getString("Name")
            findViewById<TextView>(R.id.addExecutor).setText(executorName)
        }
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val date = findViewById<TextView>(R.id.datePicker).text.toString()
        val executorName = findViewById<TextView>(R.id.addExecutor).text.toString()

        outState.putString("date", date)
        outState.putString("executorName", executorName)
        outState.putInt("executorId", executorId)
        outState.putStringArrayList("pointList", pointList)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val date = savedInstanceState.getString("date")
        val executorName = savedInstanceState.getString("executorName")
        executorId = savedInstanceState.getInt("executorId",0)
        val arr = savedInstanceState.getStringArrayList("pointList")
        pointList.addAll(arr!!.toList())

        findViewById<TextView>(R.id.datePicker).setText(date ?: "Выберите Дату")
        findViewById<TextView>(R.id.addExecutor).setText(executorName ?: "Выберите Исполнителя")
    }


}