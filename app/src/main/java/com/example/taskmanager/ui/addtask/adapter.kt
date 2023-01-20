package com.example.taskmanager.ui.addtask

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import com.example.taskmanager.R

class adapter(context: Context,val items:ArrayList<String>) : ArrayAdapter<String>(context,R.layout.todo,items) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        if(convertView == null){
            return getView(position,LayoutInflater.from(context).inflate(R.layout.todo,null),parent)
        }
        convertView.findViewById<EditText>(R.id.entryTask).addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                items.set(position,s.toString())
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                items.set(position,s.toString())
            }

            override fun afterTextChanged(s: Editable?) {
                items.set(position,s.toString())
            }

        })
        return convertView
    }
}