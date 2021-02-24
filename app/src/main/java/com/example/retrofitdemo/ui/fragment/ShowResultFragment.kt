package com.example.retrofitdemo.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.retrofitdemo.R
import com.example.retrofitdemo.data.enums.CodeStatus
import com.example.retrofitdemo.data.responseData.Codes
import com.example.retrofitdemo.data.showResultData.LayoutPost
import com.example.retrofitdemo.data.responseData.Post
import com.example.retrofitdemo.data.responseData.Posts
import com.example.retrofitdemo.ui.adapter.PostAdapter
import com.example.retrofitdemo.data.enums.MessageType
import com.example.retrofitdemo.data.showResultData.ShowResultArgs
import kotlinx.android.synthetic.main.fragment_show_result.view.*

class ShowResultFragment : Fragment() {

    companion object {
        private val bundle = Bundle()
        private val fragment = ShowResultFragment()

        fun newInstancePost(post: Post, code: Int, msgType: MessageType): ShowResultFragment {
            bundle.apply {
                putParcelable("POST", post)
                putInt("CODE", code)
                putString("MSG_TYPE", msgType.toString())
            }

            fragment.arguments = bundle
            return fragment
        }

        fun newInstanceError(
            errorMessage: String,
            code: Int,
            msgType: MessageType
        ): ShowResultFragment {
            bundle.apply {
                putString("ERROR_MESSAGE", errorMessage)
                putInt("CODE", code)
                putString("MSG_TYPE", msgType.toString())
            }

            fragment.arguments = bundle
            return fragment
        }

        fun newInstancePosts(posts: Posts, codes: Codes, msgType: MessageType): ShowResultFragment {
            bundle.apply {
                putParcelable("POSTS", posts)
                putParcelable("CODES", codes)
                putString("MSG_TYPE", msgType.toString())
            }

            fragment.arguments = bundle
            return fragment
        }

        fun getArgs(): ShowResultArgs {
            val args = ShowResultArgs().apply {
                fragment.arguments?.let {
                    this.code = it.getInt("CODE")
                    this.errorMsg = it.getString("ERROR_MESSAGE") ?: ""
                    this.msgType = it.getString("MSG_TYPE") ?: ""
                }
            }
            return args
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

        val args = getArgs()
        val list = mutableListOf<LayoutPost>()
        var index = 0

        if (args.errorMsg != "") {
            if (args.code != CodeStatus.EMPTY.value) {
                list.add(LayoutPost(Post(0, args.errorMsg), args.code))
            }
        } else if (args.msgType == MessageType.POSTS_RESULTS.toString()) {
            val posts = arguments?.getParcelable<Posts>("POSTS") as Posts
            val codes = arguments?.getParcelable<Codes>("CODES") as Codes
            posts.forEach {
                list.add(LayoutPost(Post(it.id, it.title), codes[index]))
                index++
            }
        } else if (args.msgType == MessageType.POST_RESULT.toString()) {
            val post = arguments?.getParcelable<Post>("POST") as Post
            if (args.code != CodeStatus.EMPTY.value) {
                list.add(LayoutPost(post, args.code))
            }
        }

        val adapter = PostAdapter(list)
        view.recyclerView.adapter = adapter
        view.recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }
}