package com.example.todo.ui.activity

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.*
import com.example.todo.R
import com.example.todo.db.TaskRepository
import com.example.todo.model.Task
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*

class TaskActivity : AppCompatActivity() {
   private lateinit var taskRepository: TaskRepository
   private lateinit var fabAdd:FloatingActionButton
   private lateinit var etTaskName:EditText
   private lateinit var tvDate:TextView
   private lateinit var dateLayout: LinearLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task)
        setSupportActionBar(findViewById(R.id.toolbar_task))
        supportActionBar?.title="Add TODO"
        taskRepository=TaskRepository(this)
      if(intent.extras!=null) {
        if (::etTaskName.isInitialized) {
          val task: Task = intent.extras!!.getSerializable(MainActivity.EXTRA_TASK) as Task
          etTaskName.setText(task.name)
          tvDate.text = task.date
        }
      }
        fabAdd=findViewById(R.id.fbTask)
        etTaskName=findViewById(R.id.etTaskName)
        tvDate=findViewById(R.id.tvDate)
        dateLayout=findViewById(R.id.dateLayout)

        fabAdd.setOnClickListener {
          if(intent.extras!=null){
            val task:Task= intent.extras!!.getSerializable(MainActivity.EXTRA_TASK) as Task
            val rowId=taskRepository.updateTask(Task(task.id,etTaskName.text.toString(),tvDate.text.toString()))
            if(rowId>-1){
              Toast.makeText(this,"Güncellendi",Toast.LENGTH_SHORT).show()
            }
            else{
              Toast.makeText(this,"Güncelleme başarısız",Toast.LENGTH_SHORT).show()
            }
          }
          else {
            if (!TextUtils.isEmpty(etTaskName.text.toString())) {
              val date: String =
                  (if (tvDate.text == null || tvDate.text.toString() == getString(
                          R.string.date)) "No end date "
                  else tvDate.text).toString()
              val rowId = taskRepository.insertTask(
                  Task(name = etTaskName.text.toString(), date = date))
              if (rowId > -1) {
                Toast.makeText(this, "Ekleme başarılı", Toast.LENGTH_SHORT).show()
              } else {
                Toast.makeText(this, "Ekleme başarısız", Toast.LENGTH_SHORT).show()
              }
            }
            startActivity(Intent(this, MainActivity::class.java))
          }
        }
      dateLayout.setOnClickListener { getDatePickerDialog()}
    }
     private fun getDatePickerDialog(){
        val c=Calendar.getInstance()
        val year=c.get(Calendar.YEAR)
        val month=c.get(Calendar.MONTH)
        val day=c.get(Calendar.DAY_OF_MONTH)

        val dialog=DatePickerDialog(this,{view,year,month,dayOfMonth->
            val endDate="$dayOfMonth.$month.$day"
            tvDate.text=endDate },year,month,day)

        dialog.datePicker.minDate=System.currentTimeMillis()
        dialog.show()

    }
}