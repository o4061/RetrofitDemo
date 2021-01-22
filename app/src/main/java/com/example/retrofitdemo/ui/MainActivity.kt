package com.example.retrofitdemo.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.retrofitdemo.R
import com.example.retrofitdemo.data.Codes
import com.example.retrofitdemo.data.Post
import com.example.retrofitdemo.data.Posts
import com.example.retrofitdemo.repository.Repository
import com.example.retrofitdemo.ui.fragments.CreatePostFragment
import com.example.retrofitdemo.ui.fragments.ShowResultFragment
import com.example.retrofitdemo.utils.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), Communicator {

    private val createPostFragment = CreatePostFragment()
    private val showResultFragment = ShowResultFragment()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val repository = Repository()
        val viewModelFactory = MainActivityViewModelFactory(repository)
        val viewModel =
            ViewModelProvider(this, viewModelFactory).get(MainActivityViewModel::class.java)

        // patch post
        btnPatchPost.setOnClickListener {
            if (checkNetwork()) {
                callCreatePostFragment(RequestType.PATCH)
            }
        }

        // delete post
        btnDeletePost.setOnClickListener {
            if (checkNetwork()) {
                callCreatePostFragment(RequestType.DELETE)
            }
        }

        // update post
        btnUpdatePost.setOnClickListener {
            if (checkNetwork()) {
                callCreatePostFragment(RequestType.UPDATE)
            }
        }

        // upload post
        btnUploadPost.setOnClickListener {
            if (checkNetwork()) {
                callCreatePostFragment(RequestType.PATCH)
            }
        }

        // get post
        btnGetPost.setOnClickListener {
            if (checkNetwork()) {
                callCreatePostFragment(RequestType.POST)
            }
        }

        // get posts
        btnGetPosts.setOnClickListener {
            if (checkNetwork()) {
                viewModel.getPosts()
                val posts = Posts()
                val codes = Codes()

                viewModel.postsResponse.observe(this, { response ->
                    if (response.isSuccessful) {
                        response.body()?.forEach {
                            posts.add(Post(it.id, it.title))
                            codes.add(response.code())
                        }
                        sendPostsToShowResultFragment(posts, codes)

                    } else {
                        val errorMessage = response.errorBody().toString()
                        val resultCode = response.code()

                        sendErrorToShowResultFragment(errorMessage, resultCode)
                    }
                })
            }
        }
    }

    override fun sendPostToShowResultFragment(post: Post, code: Int) {
        val bundle = Bundle()
        bundle.putSerializable("POST", post)
        bundle.putInt("CODE", code)
        bundle.putString("MSG_TYPE", MessageType.POST_RESULT.toString())

        showResultFragment.arguments = bundle

        this.supportFragmentManager.beginTransaction().apply {
            remove(showResultFragment)
            replace(R.id.frameLayout, showResultFragment)
            addToBackStack(null)
            commit()
        }
    }

    override fun sendErrorToShowResultFragment(errorMessage: String, code: Int) {
        val bundle = Bundle()
        bundle.putString("ERROR_MESSAGE", errorMessage)
        bundle.putInt("CODE", code)
        bundle.putString("MSG_TYPE", MessageType.ERROR_MESSAGE.toString())

        showResultFragment.arguments = bundle

        this.supportFragmentManager.beginTransaction().apply {
            remove(showResultFragment)
            replace(R.id.frameLayout, showResultFragment)
            addToBackStack(null)
            commit()
        }
    }

    override fun sendPostsToShowResultFragment(posts: Posts, codes: Codes) {
        val bundle = Bundle()
        bundle.putSerializable("POSTS", posts)
        bundle.putSerializable("CODES", codes)
        bundle.putString("MSG_TYPE", MessageType.POSTS_RESULTS.toString())

        showResultFragment.arguments = bundle

        this.supportFragmentManager.beginTransaction().apply {
            remove(showResultFragment)
            replace(R.id.frameLayout, showResultFragment)
            addToBackStack(null)
            commit()
        }
    }

    override fun callCreatePostFragment(requestType: RequestType) {
        val bundle = Bundle()
        bundle.putString("TYPE", requestType.toString())

        createPostFragment.arguments = bundle

        this.supportFragmentManager.beginTransaction().apply {
            remove(createPostFragment)
            replace(R.id.frameLayout, createPostFragment)
            addToBackStack(null)
            commit()
        }
    }

    fun checkNetwork(): Boolean {
        val network = NetworkListener()
        return network.checkNetworkAvailability(this)
    }
}