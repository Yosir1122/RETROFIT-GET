package com.example.retrofitget.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitget.databinding.ItemRvBinding
import com.example.retrofitget.models.MyTodo

class RvAdapter( var rvAction:RvAction,var list: List<MyTodo> = ArrayList()) : RecyclerView.Adapter<RvAdapter.Vh>() {
    inner class Vh(var itemRv: ItemRvBinding) : RecyclerView.ViewHolder(itemRv.root) {
        fun onBind(myTodo: MyTodo) {
            itemRv.izoh.text = myTodo.izoh
            itemRv.sana.text = myTodo.sana
            itemRv.bajarildi.text = myTodo.bajarildi.toString()
            itemRv.sarlavha.text = myTodo.sarlavha
            itemRv.Delete.setOnClickListener {
                rvAction.delete(myTodo)
            }
            itemRv.Edit.setOnClickListener {
                rvAction.edit(myTodo)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemRvBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int = list.size


    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position])
    }
    interface RvAction{
        fun edit(myTodo: MyTodo)
        fun delete(myTodo: MyTodo)
    }
}