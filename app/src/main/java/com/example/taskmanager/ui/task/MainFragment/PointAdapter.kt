package com.example.taskmanager.ui.task.MainFragment

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.TextView
import com.example.taskmanager.R
import com.example.taskmanager.payload.response.PointResponse
import com.example.taskmanager.payload.response.TaskResponse
import com.example.taskmanager.ui.task.TaskActivity

class PointAdapter(context : Context,list : ArrayList<PointResponse>,val editable:Boolean) : ArrayAdapter<PointResponse>(context, R.layout.point, list ) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        if(convertView == null){
            return getView(position, LayoutInflater.from(context).inflate(R.layout.point,null), parent)
        }
        val point : PointResponse? = getItem(position)
        if(point != null) {
            val chechBox : CheckBox = convertView.findViewById<CheckBox>(R.id.checkBox)
            chechBox.text = point.content
            chechBox.isClickable = editable
        }
        return convertView
    }
}