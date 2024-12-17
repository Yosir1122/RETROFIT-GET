package com.example.retrofitget.models

import retrofit2.Call

data class MyTodo(
    val id: Int,
    val bajarildi:Boolean,
    val izoh: String,
    val sana:String,
    val sarlavha:String
)