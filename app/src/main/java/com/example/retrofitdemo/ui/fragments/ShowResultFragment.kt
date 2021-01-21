package com.example.retrofitdemo.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.retrofitdemo.R
import com.example.retrofitdemo.data.Codes
import com.example.retrofitdemo.data.Model
import com.example.retrofitdemo.data.Post
import com.example.retrofitdemo.data.Posts
import com.example.retrofitdemo.adapter.CustomAdapter
import com.example.retrofitdemo.utils.MessageType
import kotlinx.android.synthetic.main.fragment_show_result.view.*


class ShowResultFragment : Fragment() {

    private lateinit var post: Post
    private lateinit var posts: Posts

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_show_result, container, false)
        val code = arguments?.getInt("CODE")
        val errorMsg = arguments?.getString("ERROR_MESSAGE")
        val msgType = arguments?.getString("MSG_TYPE")
        val listView = view.listView
        val list = mutableListOf<Model>()
        var index = 0


        if (!errorMsg.isNullOrEmpty()) {
            list.add(Model(Post(0, errorMsg), code!!.toInt()))
        } else if (msgType.equals(MessageType.POSTS_RESULTS.toString())) {
            posts = arguments?.getSerializable("POSTS") as Posts
            val codes = arguments?.getSerializable("CODES") as Codes
            posts.forEach {
                list.add(Model(Post(it.id, it.title), codes[index]))
                index++
            }

        } else if (msgType.equals(MessageType.POST_RESULT.toString())) {
            post = arguments?.getSerializable("POST") as Post
            list.add(Model(post, code!!.toInt()))
        }

        listView.adapter = CustomAdapter(context!!, R.layout.list_object, list)
        return view
    }
}