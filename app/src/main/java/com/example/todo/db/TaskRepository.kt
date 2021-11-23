package com.example.todo.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.example.todo.model.Task

class TaskRepository(var context:Context) {
  private var mDbHelper:DbHelper=DbHelper.getInstance(context)
  fun getAllTask():ArrayList<Task>{
    val list=ArrayList<Task>()
    val db=mDbHelper.readableDatabase

    val query=
        "SELECT ${DbHelper.KEY_ID},${DbHelper.KEY_NAME} , ${DbHelper.KEY_DATE} FROM ${DbHelper.TABLE_NAME}"

    val cursor:Cursor=db.rawQuery(query,null)

    if(cursor.moveToFirst()){
      do {
        val id=cursor.getInt(cursor.getColumnIndex(DbHelper.KEY_ID))
        val name=cursor.getString(cursor.getColumnIndex(DbHelper.KEY_NAME))
        val date=cursor.getString(cursor.getColumnIndex(DbHelper.KEY_DATE))

        val task=Task(id,name,date)
        list.add(task)
      }
        while (cursor.moveToNext())
    }
    cursor.close()
    db.close()
    return list
  }
  fun insertTask(task: Task):Int{
    val db=mDbHelper.writableDatabase
    val values=ContentValues()
    values.apply {
      put(DbHelper.KEY_NAME,task.name)
      put(DbHelper.KEY_DATE,task.date)
    }
    val id=db.insert(DbHelper.TABLE_NAME,null,values)
    db.close()
    return id.toInt()
  }
  fun deleteTask(taskId:Int){
    val db=mDbHelper.writableDatabase
    db.delete(DbHelper.TABLE_NAME,DbHelper.KEY_ID + "=?", arrayOf(taskId.toString()))
    db.close()
  }
  fun updateTask(task: Task): Int {
    val db=mDbHelper.writableDatabase
    val values=ContentValues()
    values.apply {
      put(DbHelper.KEY_NAME,task.name)
      put(DbHelper.KEY_DATE,task.date)
    }
    return db.update(DbHelper.TABLE_NAME,values,DbHelper.KEY_ID+"=?", arrayOf(task.id.toString()))
  }
}