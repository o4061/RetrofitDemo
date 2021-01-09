package com.example.retrofitdemo.api

import com.example.retrofitdemo.data.Post
import com.example.retrofitdemo.data.Posts
import retrofit2.Response
import retrofit2.http.*

interface JsonPlaceholderApi {

    @GET("posts/{id}")
    suspend fun getPost(@Path("id") postId: Int): Response<Post>

    @GET("posts")
    suspend fun getPosts(): Response<Posts>

    @POST("posts")
    suspend fun uploadPost(@Body post: Post): Response<Post>



}