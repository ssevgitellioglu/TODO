package com.example.todo.model

import java.io.Serializable

data class Task (
  var id:Int=0,
    var name:String,
    var date:String
):Serializable
