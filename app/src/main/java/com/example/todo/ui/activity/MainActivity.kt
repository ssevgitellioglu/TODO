package com.example.todo.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.core.view.get
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.R
import com.example.todo.adapter.TaskAdapter
import com.example.todo.db.TaskRepository
import com.example.todo.model.Task
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(),TaskAdapter.OnTaskCompleteListener ,TaskAdapter.OnTaskEditListener{
  private lateinit var taskRepository: TaskRepository
  private lateinit var taskList:ArrayList<Task>
  private lateinit var adapter:TaskAdapter
  companion object{
    const val EXTRA_TASK="extra_task"
  }
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    setSupportActionBar(findViewById(R.id.toolbar_main))
    supportActionBar?.title="TODO-List"
    supportActionBar?.hide()
    taskRepository= TaskRepository(this)
    taskList=taskRepository.getAllTask()

    val rvTask:RecyclerView=findViewById(R.id.rvTask)
    rvTask.layoutManager=LinearLayoutManager(this)
    adapter= TaskAdapter(this,taskList)
    adapter.setOnCompleteListener(this)
    adapter.setOnEditTaskListener(this)
    rvTask.adapter=adapter
    rvTask.addItemDecoration(DividerItemDecoration(this,DividerItemDecoration.VERTICAL))
   val fabAdd:FloatingActionButton=findViewById(R.id.fab)
    fabAdd.setOnClickListener { startActivity(Intent(this,TaskActivity::class.java)) }
  }
  override fun onResume() {
    super.onResume()
    taskList=taskRepository.getAllTask()
    adapter.updateList(taskList)
  }

  override fun onTaskComplete(taskId: Int) {
    taskRepository.deleteTask(taskId)
    taskList=taskRepository.getAllTask()
    adapter.updateList(taskList)
  }

  override fun onEditTask(task: Task) {
 val intent=Intent(this,TaskActivity::class.java)
    intent.putExtra(EXTRA_TASK,task)
    startActivity(intent)
  }
}