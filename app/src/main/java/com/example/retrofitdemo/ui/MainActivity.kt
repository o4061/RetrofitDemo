package com.example.retrofitdemo.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.retrofitdemo.utils.MainActivityViewModelFactory
import com.example.retrofitdemo.R
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
        viewModel.getPost(2)
        viewModel.getPosts()

        viewModel.postResponse.observe(this, Observer { response ->
            if (response.isSuccessful) {
                Log.d("Response", response.body()?.userId.toString())
                Log.d("Response", response.body()?.id.toString())
                Log.d("Response", response.body()?.title!!)
                Log.d("Response", response.body()?.body!!)
            } else {
                Log.d("Response", response.errorBody().toString())
            }
        })

        viewModel.postsResponse.observe(this, Observer { response ->
            if (response.isSuccessful) {
                response.body()?.forEach {
                    Log.d("Response", it.userId.toString())
                    Log.d("Response", it.id.toString())
                    Log.d("Response", it.title)
                    Log.d("Response", it.body)
                }
            } else {
                Log.d("Response", response.errorBody().toString())
            }
        })
    }
}