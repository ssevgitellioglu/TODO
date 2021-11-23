package com.example.todo.adapter

import android.content.Context
import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.R
import com.example.todo.model.Task

class TaskAdapter(var context: Context,var taskList: ArrayList<Task>): RecyclerView.Adapter<TaskAdapter.ViewHolder>() {
  private lateinit var onTaskCompleteListener: OnTaskCompleteListener
  private lateinit var onTaskEditListener: OnTaskEditListener
class ViewHolder(view:View):RecyclerView.ViewHolder(view){
  val taskName: TextView =view.findViewById<TextView>(R.id.taskName)
  val taskDate: TextView =view.findViewById<TextView>(R.id.taskDate)
  val completeCheck:CheckBox=view.findViewById<CheckBox>(R.id.isTaskComplete)

}
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val view: View =LayoutInflater.from(parent.context).inflate(R.layout.task_item,parent,false)
    return ViewHolder(view)
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.completeCheck.isChecked=false
    holder.taskName.text= taskList[position].name
    holder.taskDate.text=taskList[position].date
    holder.completeCheck.setOnClickListener {
      onTaskCompleteListener.let {
        it.onTaskComplete(taskList[position].id)
      }
    }
    holder.itemView.setOnCreateContextMenuListener{menu, v, menuInfo ->
      menu.add("Güncelle").setOnMenuItemClickListener {
        onTaskEditListener.let {
          it.onEditTask(taskList[position])
        }
       return@setOnMenuItemClickListener true
      }
    }
  }

  override fun getItemCount(): Int=taskList.size
  fun updateList(newList:ArrayList<Task>){
    taskList.clear()
    taskList.addAll(newList)
    notifyDataSetChanged()
  }

  fun setOnCompleteListener(onTaskCompleteListener: OnTaskCompleteListener){
    this.onTaskCompleteListener=onTaskCompleteListener
  }
  fun setOnEditTaskListener(onTaskEditListener: OnTaskEditListener){
    this.onTaskEditListener=onTaskEditListener
  }

interface OnTaskCompleteListener{
  fun onTaskComplete(taskId:Int)
}
  interface OnTaskEditListener{
    fun onEditTask(task: Task)
  }
}
