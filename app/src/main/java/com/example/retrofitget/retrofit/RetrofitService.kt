package com.example.retrofitget.retrofit

import com.example.retrofitget.models.MyTodo
import com.example.retrofitget.models.MyTodoPostRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface RetrofitService {
    @GET("rejalar")
    fun getAllTodo():Call<List<MyTodo>>

    @POST("rejalar/")
    fun addTodo(@Body myTodoPostRequest: MyTodoPostRequest):Call<MyTodo>

    @DELETE("rejalar/{id}/")
    fun deleteTodo(@Path("id") id:Int):Call<MyTodo>

    @PUT("rejalar/{id}/")
    fun editTodo(@Path("id") id:Int,@Body myTodoPostRequest: MyTodoPostRequest):Call<MyTodo>

}