package com.example.taskmanager.ui.task.MainFragment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CheckBox
import com.example.taskmanager.R
import com.example.taskmanager.api.PointApi
import com.example.taskmanager.payload.request.PointStatusRequest
import com.example.taskmanager.payload.response.PointResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import retrofit2.Response

class PointAdapter(context : Context,list : List<PointResponse>,val editable:Boolean,val mainVM : MainTaskVM) : ArrayAdapter<PointResponse>(context, R.layout.point, list ) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        if(convertView == null){
            return getView(position, LayoutInflater.from(context).inflate(R.layout.point,null), parent)
        }
        val point : PointResponse? = getItem(position)
        if(point != null) {
            val chechBox : CheckBox = convertView.findViewById<CheckBox>(R.id.checkBox)
            chechBox.text = point.content
            chechBox.isChecked = point.isDone
            chechBox.isClickable = editable
            chechBox.setOnClickListener({ mainVM.sendPointListener(point.id,chechBox.isChecked)})
        }
        return convertView
    }
}