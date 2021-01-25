package com.example.retrofitdemo.adapter

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitdemo.R
import com.example.retrofitdemo.data.Model
import kotlinx.android.synthetic.main.list_object.view.*


class MyAdapter(
    private val models: List<Model>
) : RecyclerView.Adapter<MyAdapter.MyAdapterViewHolder>() {

    inner class MyAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_object, parent, false)
        return MyAdapterViewHolder(view)
    }

    override fun getItemCount() = models.size

    override fun onBindViewHolder(holder: MyAdapterViewHolder, position: Int) {
        val currentItem = models[position]

        holder.itemView.apply {
            if (currentItem.code == 200 || currentItem.code == 201 || currentItem.code == 0) {
                postTextView.text =
                    currentItem.post.id.toString() + ") " + currentItem.post.title
                codeTextView.text = currentItem.code.toString()
                codeTextView.setTextColor(Color.GREEN)
            } else {
                postTextView.text = currentItem.post.toString()
                codeTextView.text = currentItem.code.toString()
                codeTextView.setTextColor(Color.RED)
            }
        }
    }
}