package com.example.retrofitdemo.ui.activity.startPage

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.retrofitdemo.R
import com.example.retrofitdemo.data.enums.MessageType
import com.example.retrofitdemo.data.enums.RequestType
import com.example.retrofitdemo.data.responseData.Codes
import com.example.retrofitdemo.data.responseData.Post
import com.example.retrofitdemo.data.responseData.Posts
import com.example.retrofitdemo.database.entities.PostDB
import com.example.retrofitdemo.network.NetworkListener
import com.example.retrofitdemo.repository.Repository
import com.example.retrofitdemo.ui.fragment.CreatePostFragment
import com.example.retrofitdemo.ui.fragment.ShowResultFragment
import com.example.retrofitdemo.utils.Communicator
import com.example.retrofitdemo.utils.StartPageViewModelFactory
import kotlinx.android.synthetic.main.start_page.*

class StartPage : AppCompatActivity(), Communicator {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.start_page)

        val repository = Repository()
        val viewModelFactory = StartPageViewModelFactory(repository)
        val viewModel =
            ViewModelProvider(this, viewModelFactory).get(StartPageViewModel::class.java)


        // patch post
        btnPatchPost.setOnClickListener {
            if (!isFinishing) {
                callCreatePostFragment(RequestType.PATCH)
            }
        }

        // delete post
        btnDeletePost.setOnClickListener {
            if (!isFinishing) {
                callCreatePostFragment(RequestType.DELETE)
            }
        }

        // update post
        btnUpdatePost.setOnClickListener {
            if (!isFinishing) {
                callCreatePostFragment(RequestType.UPDATE)
            }
        }

        // upload post
        btnUploadPost.setOnClickListener {
            if (!isFinishing) {
                callCreatePostFragment(RequestType.PATCH)
            }
        }

        // get post
        btnGetPost.setOnClickListener {
            if (!isFinishing) {
                callCreatePostFragment(RequestType.POST)
            }
        }

        // get posts
        btnGetPosts.setOnClickListener {
            if (!isFinishing) {
                getPostMethod(viewModel)
            }
        }
    }

    private fun getPostMethod(viewModel: StartPageViewModel) {
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
                    notifyUser("DataBase is empty")
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
        if (checkNetwork()) {
            val createPostFragment = CreatePostFragment.newInstance(requestType)
            fragmentTransaction(createPostFragment)
        } else {
            notifyUser("No internet Connection")
        }
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
        return NetworkListener().checkNetworkAvailability(this)
    }

    private fun fillDataBase(viewModel: StartPageViewModel, posts: Posts) {
        val postsDB = ArrayList<PostDB>()

        posts.forEach {
            postsDB.add(PostDB(it.id, it.title.toString()))
        }
        viewModel.addPosts(this, postsDB)
    }

    private fun notifyUser(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}