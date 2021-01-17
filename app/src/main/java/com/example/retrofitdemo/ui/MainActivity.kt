package com.example.retrofitdemo.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.retrofitdemo.utils.MainActivityViewModelFactory
import com.example.retrofitdemo.R
import com.example.retrofitdemo.data.Codes
import com.example.retrofitdemo.data.Post
import com.example.retrofitdemo.data.Posts
import com.example.retrofitdemo.repository.Repository
import com.example.retrofitdemo.utils.Communicator
import com.example.retrofitdemo.utils.MessageType
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), Communicator {

    private lateinit var viewModel: MainActivityViewModel
    private val createPostFragment = CreatePostFragment()
    private val showResultFragment = ShowResultFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val repository = Repository()
        val viewModelFactory = MainActivityViewModelFactory(repository)
        var resultPost: Post
        var resultCode: Int
        var errorMessage: String

        viewModel = ViewModelProvider(this, viewModelFactory).get(MainActivityViewModel::class.java)

        // patch post
        btnPatchPost.setOnClickListener {
            viewModel.patchPost(Post(1, "patch title"))

            viewModel.postResponse.observe(this, { response ->
                if (response.isSuccessful) {
                    resultPost = Post(response.body()!!.id, response.body()!!.title)
                    resultCode = response.code()

                    sendPostToShowResultFragment(resultPost, resultCode)
                } else {
                    errorMessage = response.errorBody().toString()
                    resultCode = response.code()

                    sendErrorToShowResultFragment(errorMessage, resultCode)
                }
            })
        }


        // delete post
        btnDeletePost.setOnClickListener {
            viewModel.deletePost(1)

            viewModel.postResponse.observe(this, { response ->
                if (response.isSuccessful) {
                    resultPost = Post(9, "The post deleted")
                    resultCode = response.code()

                    sendPostToShowResultFragment(resultPost, resultCode)
                } else {
                    errorMessage = response.errorBody().toString()
                    resultCode = response.code()

                    sendErrorToShowResultFragment(errorMessage, resultCode)
                }
            })
        }


        // update post
        btnUpdatePost.setOnClickListener {
            viewModel.updatePost(Post(13, "changed!!!!"))

            viewModel.postResponse.observe(this, { response ->
                if (response.isSuccessful) {
                    resultPost = Post(response.body()!!.id, response.body()!!.title)
                    resultCode = response.code()

                    sendPostToShowResultFragment(resultPost, resultCode)
                } else {
                    errorMessage = response.errorBody().toString()
                    resultCode = response.code()

                    sendErrorToShowResultFragment(errorMessage, resultCode)
                }
            })
        }


        // get post
        btnGetPost.setOnClickListener {
            viewModel.getPost(2)

            viewModel.postResponse.observe(this, { response ->
                if (response.isSuccessful) {
                    resultPost = Post(response.body()!!.id, response.body()!!.title)
                    resultCode = response.code()

                    sendPostToShowResultFragment(resultPost, resultCode)
                } else {
                    errorMessage = response.errorBody().toString()
                    resultCode = response.code()

                    sendErrorToShowResultFragment(errorMessage, resultCode)
                }
            })
        }


        // get posts
        btnGetPosts.setOnClickListener {
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
                    errorMessage = response.errorBody().toString()
                    resultCode = response.code()

                    sendErrorToShowResultFragment(errorMessage, resultCode)
                }
            })
        }

        // upload post
        btnUploadPost.setOnClickListener {
            viewModel.uploadPost(Post(6, "my new post"))

            viewModel.postResponse.observe(this, { response ->
                if (response.isSuccessful) {
                    resultPost = Post(response.body()!!.id, response.body()!!.title)
                    resultCode = response.code()

                    sendPostToShowResultFragment(resultPost, resultCode)
                } else {
                    errorMessage = response.errorBody().toString()
                    resultCode = response.code()

                    sendErrorToShowResultFragment(errorMessage, resultCode)
                }
            })
        }
    }

    override fun sendPostToShowResultFragment(post: Post, code: Int) {
        val bundle = Bundle()
        bundle.putSerializable("POST", post)
        bundle.putInt("CODE", code)
        bundle.putString("MSG_TYPE", MessageType.POST_RESULT.toString())
        showResultFragment.arguments = bundle

        this.supportFragmentManager.beginTransaction().apply {
            replace(R.id.frameLayout, showResultFragment)
            addToBackStack(null)
            commit()
        }
    }

    override fun sendErrorToShowResultFragment(
        errorMessage: String,
        code: Int
    ) {
        val bundle = Bundle()
        bundle.putString("ERROR_MESSAGE", errorMessage)
        bundle.putInt("CODE", code)
        bundle.putString("MSG_TYPE", MessageType.ERROR_MESSAGE.toString())

        showResultFragment.arguments = bundle

        this.supportFragmentManager.beginTransaction().apply {
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
            replace(R.id.frameLayout, showResultFragment)
            addToBackStack(null)
            commit()
        }
    }
}