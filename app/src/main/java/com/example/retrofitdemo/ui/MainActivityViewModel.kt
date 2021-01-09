package com.example.retrofitdemo.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofitdemo.data.Post
import com.example.retrofitdemo.data.Posts
import com.example.retrofitdemo.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class MainActivityViewModel(private val repository: Repository): ViewModel() {


    val postResponse: MutableLiveData<Response<Post>> = MutableLiveData()
    val postsResponse: MutableLiveData<Response<Posts>> = MutableLiveData()

    fun getPost(postId:Int){
        viewModelScope.launch {
            val response = repository.getPost(postId)
            postResponse.value = response
        }
    }

    fun getPosts(){
        viewModelScope.launch {
            val response = repository.getPosts()
            postsResponse.value = response
        }
    }
}