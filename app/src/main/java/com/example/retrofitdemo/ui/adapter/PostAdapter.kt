package com.example.retrofitdemo.ui.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitdemo.R
import com.example.retrofitdemo.data.LayoutPost
import kotlinx.android.synthetic.main.post_object.view.*

class PostAdapter(
    private val layoutPosts: List<LayoutPost>
) : RecyclerView.Adapter<PostAdapter.MyAdapterViewHolder>() {

    inner class MyAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.post_object, parent, false)
        return MyAdapterViewHolder(view)
    }

    override fun getItemCount() = layoutPosts.size

    override fun onBindViewHolder(holder: MyAdapterViewHolder, position: Int) {
        val currentItem = layoutPosts[position]

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