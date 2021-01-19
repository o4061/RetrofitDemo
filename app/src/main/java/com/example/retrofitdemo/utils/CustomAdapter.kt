package com.example.retrofitdemo.utils

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.retrofitdemo.R
import com.example.retrofitdemo.data.Model

class CustomAdapter(var mCtx: Context, var resources: Int, var items: List<Model>) :
    ArrayAdapter<Model>(mCtx, resources, items) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        val view: View = layoutInflater.inflate(resources, null)

        val postTextView: TextView = view.findViewById(R.id.postTextView)
        val codeTextView: TextView = view.findViewById(R.id.codeTextView)

        val mItem: Model = items[position]

        if (mItem.code == 200 || mItem.code == 201) {
            postTextView.text = mItem.post.id.toString() + ") " + mItem.post.title
            codeTextView.text = mItem.code.toString()
            codeTextView.setTextColor(Color.GREEN)
        } else if (mItem.code != 200 && mItem.code != 201) {
            postTextView.text = mItem.post.toString()
            codeTextView.text = mItem.code.toString()
            codeTextView.setTextColor(Color.RED)
        }

        return view
    }
}