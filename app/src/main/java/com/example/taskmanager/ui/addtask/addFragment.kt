package com.example.taskmanager.ui.addtask

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.FragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.taskmanager.R
import com.example.taskmanager.payload.response.BaseResponse
import com.example.taskmanager.ui.authorize.Login.LoginVM
import com.example.taskmanager.ui.mainmenu.MainMenu
import java.util.*
import kotlin.collections.ArrayList

class addFragment : Fragment() {
    private lateinit var vm: addVM
    private val takeExecutorFragment : TakeExecutorFragment = TakeExecutorFragment(this)
    private lateinit var loadingDialog : AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setCancelable(false)
        builder.setView(R.layout.loading_dialog)
        loadingDialog = builder.create()
        if(savedInstanceState == null)
            Log.println(Log.INFO,"TAsk","Новый")
        else{
            Log.println(Log.INFO,"Tsak","Старый")
        }
    }

    private lateinit var adapter: adapter;
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val convertView = inflater.inflate(R.layout.fragment_add, container, false)
        //Point ListVIew
        vm = ViewModelProvider(this).get(addVM::class.java)
        adapter = adapter(requireContext(), vm.listItems)
        val listTodo: ListView = convertView.findViewById(R.id.toDoList)
        listTodo.setDivider(null);
        val addButton = inflater.inflate(R.layout.add_point, null)
        addButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                vm.listItems.add("")
                adapter.notifyDataSetChanged()
            }
        })
        listTodo.addFooterView(addButton)
        listTodo.adapter = adapter



        // Calendar
        val c = Calendar.getInstance()
        val d = c.get(Calendar.DAY_OF_MONTH)
        val m = c.get(Calendar.MONTH)
        val y = c.get(Calendar.YEAR)
        val date = convertView.findViewById<TextView>(R.id.datePicker)
        date.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val dpd = DatePickerDialog(context!!, { view, year, month, dayOfMonth ->
                    convertView.findViewById<TextView>(R.id.datePicker).text =
                        "$dayOfMonth.$month.$year"
                }, y, m, d)
                dpd.show()
            }
        })

        //Executor Fragment
        convertView.findViewById<TextView>(R.id.addExecutor)
            .setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View?) {
                    vm.date = convertView.findViewById<TextView>(R.id.datePicker).text.toString()
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.addFragmentContriner, takeExecutorFragment).commit()
                }
            })

        //Get Result Executor Fragment
        requireActivity().supportFragmentManager.setFragmentResultListener(
            "user",
            viewLifecycleOwner,
            object : FragmentResultListener {
                override fun onFragmentResult(requestKey: String, result: Bundle) {
                    convertView.findViewById<TextView>(R.id.datePicker).setText(vm.date)
                    vm.executorId = result.getInt("userId")
                    vm.executorName = result.getString("firstName") + " " + result.getString("secondName")
                    convertView.findViewById<TextView>(R.id.addExecutor)
                        .setText(vm.executorName)
                }
            })
        convertView.findViewById<Button>(R.id.saveButtonFromAdd).setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                vm.date = convertView.findViewById<TextView>(R.id.datePicker).text.toString()
                vm.executorName = convertView.findViewById<TextView>(R.id.addExecutor).text.toString()
                vm.title = convertView.findViewById<EditText>(R.id.addTitle).text.toString()
                vm.description = convertView.findViewById<EditText>(R.id.addDescription).text.toString()
                vm.saveTask()
                vm.saveResult.observe(viewLifecycleOwner){
                    when(it){
                        is BaseResponse.Loading -> {
                            loadingDialog.show();
                        }
                        is BaseResponse.Success -> {
                            loadingDialog.dismiss()
                            startActivity(Intent(requireActivity(), MainMenu::class.java))
                        }
                        is BaseResponse.Error -> {
                            Toast.makeText(requireContext(),"Ошибка Отправления", Toast.LENGTH_LONG).show()
                            loadingDialog.dismiss()
                        }
                    }
                }
            }

        })
        return convertView;
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        vm.title = view?.findViewById<EditText>(R.id.addTitle)?.text.toString()
        vm.executorName = view?.findViewById<TextView>(R.id.addExecutor)?.text.toString()
        vm.description = view?.findViewById<EditText>(R.id.addDescription)?.text.toString()
        vm.date = view?.findViewById<TextView>(R.id.datePicker)?.text.toString()
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        view?.findViewById<EditText>(R.id.addTitle)?.setText(vm.title)
        view?.findViewById<TextView>(R.id.addExecutor)?.setText(vm.executorName)
        view?.findViewById<EditText>(R.id.addDescription)?.setText(vm.description)
        view?.findViewById<TextView>(R.id.datePicker)?.setText(vm.date)
    }

}