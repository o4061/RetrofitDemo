package com.example.retrofitdemo.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.retrofitdemo.R
import com.example.retrofitdemo.data.Codes
import com.example.retrofitdemo.data.Post
import com.example.retrofitdemo.data.Posts
import com.example.retrofitdemo.entities.PostDB
import com.example.retrofitdemo.repository.Repository
import com.example.retrofitdemo.ui.fragments.CreatePostFragment
import com.example.retrofitdemo.ui.fragments.ShowResultFragment
import com.example.retrofitdemo.utils.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), Communicator {

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
            } else
                Toast.makeText(this, "No internet Connection", Toast.LENGTH_SHORT).show()
        }

        // delete post
        btnDeletePost.setOnClickListener {
            if (checkNetwork()) {
                callCreatePostFragment(RequestType.DELETE)
            } else
                Toast.makeText(this, "No internet Connection", Toast.LENGTH_SHORT).show()
        }

        // update post
        btnUpdatePost.setOnClickListener {
            if (checkNetwork()) {
                callCreatePostFragment(RequestType.UPDATE)
            } else
                Toast.makeText(this, "No internet Connection", Toast.LENGTH_SHORT).show()
        }

        // upload post
        btnUploadPost.setOnClickListener {
            if (checkNetwork()) {
                callCreatePostFragment(RequestType.PATCH)
            } else
                Toast.makeText(this, "No internet Connection", Toast.LENGTH_SHORT).show()
        }

        // get post
        btnGetPost.setOnClickListener {
            if (checkNetwork()) {
                callCreatePostFragment(RequestType.POST)
            } else
                Toast.makeText(this, "No internet Connection", Toast.LENGTH_SHORT).show()
        }

        // get posts
        btnGetPosts.setOnClickListener {
            val posts = Posts()
            val codes = Codes()
            if (checkNetwork()) {
                viewModel.getPosts()

                viewModel.postsResponse.observe(this, { response ->
                    if (response.isSuccessful) {
                        response.body()?.forEach {
                            posts.add(Post(it.id, it.title))
                            codes.add(response.code())
                        }
                        fillDataBase(viewModel, posts)
                        sendPostsToShowResultFragment(posts, codes)

                    } else {
                        val errorMessage = response.errorBody().toString()
                        val resultCode = response.code()

                        sendErrorToShowResultFragment(errorMessage, resultCode)
                    }
                })
            } else {
                viewModel.getPostsFromDb(this)
                viewModel.postsDb.observe(this, { response ->
                    if (response.isEmpty()) {
                        Toast.makeText(this, "DataBase is empty", Toast.LENGTH_SHORT).show()
                    } else {
                        response.forEach {
                            posts.add(Post(it.id, it.title))
                            codes.add(0)
                        }
                        sendPostsToShowResultFragment(posts, codes)
                    }
                })
            }
        }
    }

    override fun sendPostToShowResultFragment(post: Post, code: Int) {
        val showResultFragment =
            ShowResultFragment.newInstancePost(post, code, MessageType.POST_RESULT)

        fragmentTransaction(showResultFragment)
    }

    override fun sendErrorToShowResultFragment(errorMessage: String, code: Int) {
        val showResultFragment =
            ShowResultFragment.newInstanceError(errorMessage, code, MessageType.ERROR_MESSAGE)

        fragmentTransaction(showResultFragment)
    }

    override fun sendPostsToShowResultFragment(posts: Posts, codes: Codes) {
        val showResultFragment =
            ShowResultFragment.newInstancePosts(posts, codes, MessageType.POSTS_RESULTS)

        fragmentTransaction(showResultFragment)
    }

    override fun callCreatePostFragment(requestType: RequestType) {
        val createPostFragment = CreatePostFragment.newInstance(requestType)

        fragmentTransaction(createPostFragment)
    }

    private fun fragmentTransaction(fragment: Fragment) {
        this.supportFragmentManager.beginTransaction().apply {
            remove(fragment)
            replace(R.id.frameLayout, fragment)
            addToBackStack(null)
            commit()
        }
    }

    private fun checkNetwork(): Boolean {
        val network = NetworkListener()
        return network.checkNetworkAvailability(this)
    }

    private fun fillDataBase(viewModel: MainActivityViewModel, posts: Posts) {
        val postsDB = ArrayList<PostDB>()

        posts.forEach {
            postsDB.add(PostDB(it.id, it.title.toString()))
        }
        viewModel.addPosts(this, postsDB)
    }
}