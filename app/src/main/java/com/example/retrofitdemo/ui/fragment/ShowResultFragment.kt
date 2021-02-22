package com.example.retrofitdemo.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.retrofitdemo.R
import com.example.retrofitdemo.data.Codes
import com.example.retrofitdemo.data.LayoutPost
import com.example.retrofitdemo.data.Post
import com.example.retrofitdemo.data.Posts
import com.example.retrofitdemo.ui.adapter.PostAdapter
import com.example.retrofitdemo.data.enums.MessageType
import kotlinx.android.synthetic.main.fragment_show_result.view.*

class ShowResultFragment : Fragment() {

    companion object {
        private val bundle = Bundle()
        fun newInstancePost(post: Post, code: Int, msgType: MessageType): ShowResultFragment {
            bundle.putParcelable("POST", post)
            bundle.putInt("CODE", code)
            bundle.putString("MSG_TYPE", msgType.toString())

            val fragment = ShowResultFragment()
            fragment.arguments = bundle
            return fragment
        }

        fun newInstanceError(
            errorMessage: String,
            code: Int,
            msgType: MessageType
        ): ShowResultFragment {
            bundle.putString("ERROR_MESSAGE", errorMessage)
            bundle.putInt("CODE", code)
            bundle.putString("MSG_TYPE", msgType.toString())

            val fragment = ShowResultFragment()
            fragment.arguments = bundle
            return fragment
        }

        fun newInstancePosts(posts: Posts, codes: Codes, msgType: MessageType): ShowResultFragment {
            bundle.putParcelable("POSTS", posts)
            bundle.putParcelable("CODES", codes)
            bundle.putString("MSG_TYPE", msgType.toString())

            val fragment = ShowResultFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_show_result, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val code = arguments?.getInt("CODE")
        val errorMsg = arguments?.getString("ERROR_MESSAGE")
        val msgType = arguments?.getString("MSG_TYPE")
        val list = mutableListOf<LayoutPost>()
        var index = 0


        if (!errorMsg.isNullOrEmpty()) {
            if (code != null) {
                list.add(LayoutPost(Post(0, errorMsg), code.toInt()))
            }
        } else if (msgType.equals(MessageType.POSTS_RESULTS.toString())) {
            val posts = arguments?.getParcelable<Posts>("POSTS") as Posts
            val codes = arguments?.getParcelable<Codes>("CODES") as Codes
            posts.forEach {
                list.add(LayoutPost(Post(it.id, it.title), codes[index]))
                index++
            }
        } else if (msgType.equals(MessageType.POST_RESULT.toString())) {
            val post = arguments?.getParcelable<Post>("POST") as Post
            if (code != null) {
                list.add(LayoutPost(post, code.toInt()))
            }
        }

        val adapter = PostAdapter(list)
        view.recyclerView.adapter = adapter
        view.recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }
}