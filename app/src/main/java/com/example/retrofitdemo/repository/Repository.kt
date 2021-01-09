package com.example.retrofitdemo.repository

import com.example.retrofitdemo.api.RetrofitInstance
import com.example.retrofitdemo.data.Post
import com.example.retrofitdemo.data.Posts
import retrofit2.Response

class Repository {
    suspend fun getPost(postId: Int): Response<Post> {
        return RetrofitInstance.api.getPost(postId)
    }

    suspend fun getPosts(): Response<Posts> {
        return RetrofitInstance.api.getPosts()
    }
}