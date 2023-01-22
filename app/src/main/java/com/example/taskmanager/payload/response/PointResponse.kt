package com.example.taskmanager.payload.response

data class PointResponse (
    val id : Int,
    val content : String,
    val isDone : Boolean,
    val taskId : Int
) : Comparable<PointResponse>{
    override fun compareTo(other: PointResponse): Int {
        return this.id - other.id
    }

}
