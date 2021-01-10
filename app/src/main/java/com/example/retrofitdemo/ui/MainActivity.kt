package com.example.retrofitdemo.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.example.retrofitdemo.utils.MainActivityViewModelFactory
import com.example.retrofitdemo.R
import com.example.retrofitdemo.data.Post
import com.example.retrofitdemo.repository.Repository
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val repository = Repository()
        val viewModelFactory = MainActivityViewModelFactory(repository)

        viewModel = ViewModelProvider(this, viewModelFactory).get(MainActivityViewModel::class.java)

        btnPatchPost.setOnClickListener {
            textView.text = null
            viewModel.patchPost(Post(1, "patch title"))

            viewModel.postResponse.observe(this, { response ->
                if (response.isSuccessful) {
                    textView.append(response.body().toString() + "\n")
                    textView.append("code: " + response.code().toString())
                } else {
                    textView.text = response.errorBody().toString()
                }
            })
        }



        btnDeletePost.setOnClickListener {
            textView.text = null
            viewModel.deletePost(1)

            viewModel.postResponse.observe(this, { response ->
                if (response.isSuccessful) {
                    textView.append("The post has been deleted!! \n")
                    textView.append("code: " + response.code().toString())
                } else {
                    textView.text = response.errorBody().toString()
                }
            })
        }


        btnUpdatePost.setOnClickListener {
            textView.text = null
            viewModel.updatePost(Post(3, "changed!!!!"))

            viewModel.postResponse.observe(this, { response ->
                if (response.isSuccessful) {
                    textView.append(response.body().toString() + "\n")
                    textView.append("code: " + response.code().toString())
                } else {
                    textView.text = response.errorBody().toString()
                }
            })
        }

        btnGetPost.setOnClickListener {
            textView.text = null
            viewModel.getPost(2)

            viewModel.postResponse.observe(this, { response ->
                if (response.isSuccessful) {
                    textView.append(response.body().toString() + "\n")
                    textView.append("code: " + response.code().toString())
                } else {
                    textView.text = response.errorBody().toString()
                }
            })
        }


        btnGetPosts.setOnClickListener {
            textView.text = null
            viewModel.getPosts()

            viewModel.postsResponse.observe(this, { response ->
                if (response.isSuccessful) {
                    response.body()?.forEach {
                        textView.append(it.toString() + " code: " + response.code() + "\n")
                    }
                } else {
                    Log.d("Response", response.errorBody().toString())
                }
            })
        }


        btnUploadPost.setOnClickListener {
            textView.text = null
            viewModel.uploadPost(Post(6, "my new post"))

            viewModel.postResponse.observe(this, { response ->
                if (response.isSuccessful) {
                    textView.append(response.body().toString() + "\n")
                    textView.append("code: " + response.code().toString())
                } else {
                    Log.d("M", "Error!!!!")
                }
            })
        }
    }
}