package com.example.retrofitdemo.api

import com.example.retrofitdemo.data.Post
import com.example.retrofitdemo.data.Posts
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface SimpleApi {

    @GET("posts/{id}")
    suspend fun getPost(@Path("id") postId : Int): Response<Post>

    @GET("posts")
    suspend fun getPosts(): Response<Posts>
}