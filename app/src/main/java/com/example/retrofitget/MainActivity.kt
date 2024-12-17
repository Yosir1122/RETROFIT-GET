package com.example.retrofitget

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.retrofitget.adapter.RvAdapter
import com.example.retrofitget.databinding.ActivityMainBinding
import com.example.retrofitget.databinding.ItemDialogBinding
import com.example.retrofitget.models.MyTodo
import com.example.retrofitget.models.MyTodoPostRequest
import com.example.retrofitget.retrofit.MyApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var rvAdapter: RvAdapter
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.add.setOnClickListener {
            val dialog = AlertDialog.Builder(this).create()
            val itemDialog = ItemDialogBinding.inflate(layoutInflater)
            itemDialog.btnSave.setOnClickListener {
                val myTodoPostRequest = MyTodoPostRequest(
                    itemDialog.bajarildi.isChecked,
                    itemDialog.edtIzoh.text.toString(),
                    itemDialog.edtTitle.text.toString()
                )
                MyApiClient.retrofitService(this).addTodo(myTodoPostRequest)
                    .enqueue(object : Callback<MyTodo> {
                        override fun onResponse(call: Call<MyTodo>, response: Response<MyTodo>) {
                            Toast.makeText(this@MainActivity, "Saqlandi", Toast.LENGTH_SHORT).show()
                            onResume()
                            dialog.cancel()
                        }

                        override fun onFailure(call: Call<MyTodo>, t: Throwable) {
                            Toast.makeText(
                                this@MainActivity,
                                "Xatolik yuz berdi",
                                Toast.LENGTH_SHORT
                            ).show()

                        }
                    })
            }
            dialog.setView(itemDialog.root)
            dialog.show()


        }
    }

    override fun onResume() {
        super.onResume()
        MyApiClient.retrofitService(this)
            .getAllTodo().enqueue(object : Callback<List<MyTodo>> {
                override fun onResponse(
                    call: Call<List<MyTodo>>,
                    response: Response<List<MyTodo>>
                ) {
                    if (response.isSuccessful) {
                        val list = response.body()
                        rvAdapter = RvAdapter(object : RvAdapter.RvAction {
                            override fun edit(myTodo: MyTodo) {
                                val dialog = AlertDialog.Builder(this@MainActivity).create()
                                val itemDialog = ItemDialogBinding.inflate(layoutInflater)
                                itemDialog.apply {
                                    edtTitle.setText(myTodo.sarlavha)
                                    edtIzoh.setText(myTodo.izoh)
                                    if (myTodo.bajarildi){
                                        bajarildi.isChecked=true
                                    }else{
                                        bajarildi.isChecked=false
                                    }
                                    btnSave.setOnClickListener {
                                        val myTodoPostRequest = MyTodoPostRequest(
                                            bajarildi.isChecked,
                                            edtIzoh.text.toString(),
                                            edtTitle.text.toString()
                                        )
                                        MyApiClient.retrofitService(this@MainActivity)
                                            .editTodo(myTodo.id, myTodoPostRequest)
                                            .enqueue(object : Callback<MyTodo> {
                                                override fun onResponse(
                                                    call: Call<MyTodo>,
                                                    response: Response<MyTodo>
                                                ) {
                                                    Toast.makeText(
                                                        this@MainActivity,
                                                        "Ozgartirildi",
                                                        Toast.LENGTH_SHORT
                                                    )
                                                        .show()
                                                    onResume()
                                                    dialog.cancel()
                                                }

                                                override fun onFailure(
                                                    call: Call<MyTodo>,
                                                    t: Throwable
                                                ) {
                                                    Toast.makeText(
                                                        this@MainActivity,
                                                        "Xatolik yuz berdi",
                                                        Toast.LENGTH_SHORT
                                                    )
                                                        .show()
                                                }
                                            })
                                    }
                                }
                                dialog.setView(itemDialog.root)
                                dialog.show()
                            }

                            override fun delete(myTodo: MyTodo) {
                                MyApiClient.retrofitService(this@MainActivity).deleteTodo(myTodo.id)
                                    .enqueue(object : Callback<MyTodo> {
                                        override fun onResponse(
                                            call: Call<MyTodo>,
                                            response: Response<MyTodo>
                                        ) {
                                            Toast.makeText(
                                                this@MainActivity,
                                                "O'chirildi",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            onResume()
                                        }

                                        override fun onFailure(call: Call<MyTodo>, t: Throwable) {
                                            Toast.makeText(
                                                this@MainActivity,
                                                "Xatolik yuz berdi",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    })
                            }
                        }, list!!)
                        binding.rv.adapter = rvAdapter
                    }
                }

                override fun onFailure(call: Call<List<MyTodo>>, t: Throwable) {
                    Toast.makeText(this@MainActivity, "Xatolik yuz berdi", Toast.LENGTH_SHORT)
                        .show()
                }
            })
    }
}
